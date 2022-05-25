import { Debouncer } from '@polymer/polymer/lib/utils/debounce.js';
import { timeOut } from '@polymer/polymer/lib/utils/async.js';
import { ComboBoxPlaceholder } from '@vaadin/combo-box/src/vaadin-combo-box-placeholder.js';

(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Combo Box');
  };

  window.Vaadin.Flow.comboBoxConnector = {
    initLazy: (comboBox) =>
      tryCatchWrapper(function (comboBox) {
        // Check whether the connector was already initialized for the ComboBox
        if (comboBox.$connector) {
          return;
        }

        comboBox.$connector = {};

        // <vaadin-multi-select-combo-box> wraps a regular combo box internally,
        // to reuse logic between both components we need to identify the
        // element that implements the data provider mixin
        const dataProviderMixin =
          comboBox.localName === 'vaadin-multi-select-combo-box' ? comboBox.$.comboBox : comboBox;

        // holds pageIndex -> callback pairs of subsequent indexes (current active range)
        const pageCallbacks = {};
        let cache = {};
        let lastFilter = '';
        const placeHolder = new window.Vaadin.ComboBoxPlaceholder();
        const MAX_RANGE_COUNT = Math.max(comboBox.pageSize * 2, 500); // Max item count in active range

        const serverFacade = (() => {
          // Private variables
          let lastFilterSentToServer = '';
          let dataCommunicatorResetNeeded = false;

          // Public methods
          const needsDataCommunicatorReset = () => (dataCommunicatorResetNeeded = true);
          const getLastFilterSentToServer = () => lastFilterSentToServer;
          const requestData = (startIndex, endIndex, params) => {
            const count = endIndex - startIndex;
            const filter = params.filter;

            comboBox.$server.setRequestedRange(startIndex, count, filter);
            lastFilterSentToServer = filter;
            if (dataCommunicatorResetNeeded) {
              comboBox.$server.resetDataCommunicator();
              dataCommunicatorResetNeeded = false;
            }
          };

          return {
            needsDataCommunicatorReset,
            getLastFilterSentToServer,
            requestData
          };
        })();

        const clearPageCallbacks = (pages = Object.keys(pageCallbacks)) => {
          // Flush and empty the existing requests
          pages.forEach((page) => {
            pageCallbacks[page]([], dataProviderMixin.size);
            delete pageCallbacks[page];

            // Empty the comboBox's internal cache without invoking observers by filling
            // the filteredItems array with placeholders (comboBox will request for data when it
            // encounters a placeholder)
            const pageStart = parseInt(page) * dataProviderMixin.pageSize;
            const pageEnd = pageStart + dataProviderMixin.pageSize;
            const end = Math.min(pageEnd, dataProviderMixin.filteredItems.length);
            for (let i = pageStart; i < end; i++) {
              dataProviderMixin.filteredItems[i] = placeHolder;
            }
          });
        };

        comboBox.dataProvider = function (params, callback) {
          if (params.pageSize != comboBox.pageSize) {
            throw 'Invalid pageSize';
          }

          if (comboBox._clientSideFilter) {
            // For clientside filter we first make sure we have all data which we also
            // filter based on comboBox.filter. While later we only filter clientside data.

            if (cache[0]) {
              performClientSideFilter(cache[0], params.filter, callback);
              return;
            } else {
              // If client side filter is enabled then we need to first ask all data
              // and filter it on client side, otherwise next time when user will
              // input another filter, eg. continue to type, the local cache will be only
              // what was received for the first filter, which may not be the whole
              // data from server (keep in mind that client side filter is enabled only
              // when the items count does not exceed one page).
              params.filter = '';
            }
          }

          const filterChanged = params.filter !== lastFilter;
          if (filterChanged) {
            cache = {};
            lastFilter = params.filter;
            this._debouncer = Debouncer.debounce(this._debouncer, timeOut.after(500), () => {
              if (serverFacade.getLastFilterSentToServer() === params.filter) {
                // Fixes the case when the filter changes
                // to something else and back to the original value
                // within debounce timeout, and the
                // DataCommunicator thinks it doesn't need to send data
                serverFacade.needsDataCommunicatorReset();
              }
              if (params.filter !== lastFilter) {
                throw new Error("Expected params.filter to be '" + lastFilter + "' but was '" + params.filter + "'");
              }
              // Call the method again after debounce.
              clearPageCallbacks();
              comboBox.dataProvider(params, callback);
            });
            return;
          }

          if (cache[params.page]) {
            // This may happen after skipping pages by scrolling fast
            commitPage(params.page, callback);
          } else {
            pageCallbacks[params.page] = callback;
            const activePages = Object.keys(pageCallbacks).map((page) => parseInt(page));
            const rangeMin = Math.min(...activePages);
            const rangeMax = Math.max(...activePages);

            if (activePages.length * params.pageSize > MAX_RANGE_COUNT) {
              if (params.page === rangeMin) {
                clearPageCallbacks([String(rangeMax)]);
              } else {
                clearPageCallbacks([String(rangeMin)]);
              }
              comboBox.dataProvider(params, callback);
            } else if (rangeMax - rangeMin + 1 !== activePages.length) {
              // Wasn't a sequential page index, clear the cache so combo-box will request for new pages
              clearPageCallbacks();
            } else {
              // The requested page was sequential, extend the requested range
              const startIndex = params.pageSize * rangeMin;
              const endIndex = params.pageSize * (rangeMax + 1);

              if (!this._debouncer || !this._debouncer.isActive()) {
                serverFacade.requestData(startIndex, endIndex, params);
              } else {
                this._debouncer = Debouncer.debounce(this._debouncer, timeOut.after(200), () =>
                  serverFacade.requestData(startIndex, endIndex, params)
                );
              }
            }
          }
        };

        comboBox.$connector.clear = tryCatchWrapper((start, length) => {
          const firstPageToClear = Math.floor(start / comboBox.pageSize);
          const numberOfPagesToClear = Math.ceil(length / comboBox.pageSize);

          for (let i = firstPageToClear; i < firstPageToClear + numberOfPagesToClear; i++) {
            delete cache[i];
          }
        });

        comboBox.$connector.filter = tryCatchWrapper(function (item, filter) {
          filter = filter ? filter.toString().toLowerCase() : '';
          return comboBox._getItemLabel(item, comboBox.itemLabelPath).toString().toLowerCase().indexOf(filter) > -1;
        });

        comboBox.$connector.set = tryCatchWrapper(function (index, items, filter) {
          if (filter != serverFacade.getLastFilterSentToServer()) {
            return;
          }

          if (index % comboBox.pageSize != 0) {
            throw 'Got new data to index ' + index + ' which is not aligned with the page size of ' + comboBox.pageSize;
          }

          if (index === 0 && items.length === 0 && pageCallbacks[0]) {
            // Makes sure that the dataProvider callback is called even when server
            // returns empty data set (no items match the filter).
            cache[0] = [];
            return;
          }

          const firstPageToSet = index / comboBox.pageSize;
          const updatedPageCount = Math.ceil(items.length / comboBox.pageSize);

          for (let i = 0; i < updatedPageCount; i++) {
            let page = firstPageToSet + i;
            let slice = items.slice(i * comboBox.pageSize, (i + 1) * comboBox.pageSize);

            cache[page] = slice;
          }
        });

        comboBox.$connector.updateData = tryCatchWrapper(function (items) {
          // IE11 doesn't work with the transpiled version of the forEach.
          for (let i = 0; i < items.length; i++) {
            let item = items[i];

            for (let j = 0; j < comboBox.filteredItems.length; j++) {
              if (comboBox.filteredItems[j].key === item.key) {
                comboBox.set('filteredItems.' + j, item);
                break;
              }
            }
          }
        });

        comboBox.$connector.updateSize = tryCatchWrapper(function (newSize) {
          if (!comboBox._clientSideFilter) {
            // FIXME: It may be that this size set is unnecessary, since when
            // providing data to combobox via callback we may use data's size.
            // However, if this size reflect the whole data size, including
            // data not fetched yet into client side, and combobox expect it
            // to be set as such, the at least, we don't need it in case the
            // filter is clientSide only, since it'll increase the height of
            // the popup at only at first user filter to this size, while the
            // filtered items count are less.
            comboBox.size = newSize;
          }
        });

        comboBox.$connector.reset = tryCatchWrapper(function () {
          clearPageCallbacks();
          cache = {};
          dataProviderMixin.clearCache();
        });

        comboBox.$connector.confirm = tryCatchWrapper(function (id, filter) {
          if (filter != serverFacade.getLastFilterSentToServer()) {
            return;
          }

          // We're done applying changes from this batch, resolve pending
          // callbacks
          let activePages = Object.getOwnPropertyNames(pageCallbacks);
          for (let i = 0; i < activePages.length; i++) {
            let page = activePages[i];

            if (cache[page]) {
              commitPage(page, pageCallbacks[page]);
            }
          }

          // Let server know we're done
          comboBox.$server.confirmUpdate(id);
        });

        comboBox.$connector.enableClientValidation = tryCatchWrapper(function (enable) {
          if (comboBox.$) {
            if (enable) {
              enableClientValidation(comboBox);
            } else {
              disableClientValidation(comboBox);
            }

            comboBox.validate();
          } else {
            setTimeout(function () {
              comboBox.$connector.enableClientValidation(enable);
            }, 10);
          }
        });

        const disableClientValidation = tryCatchWrapper(function (combo) {
          if (typeof combo.$checkValidity == 'undefined') {
            combo.$checkValidity = combo.checkValidity;
            combo.checkValidity = function () {
              return !comboBox.invalid;
            };
          }
          if (typeof combo.$validate == 'undefined') {
            combo.$validate = combo.validate;
            combo.validate = function () {
              return !(comboBox.focusElement.invalid = comboBox.invalid);
            };
          }
        });

        const enableClientValidation = tryCatchWrapper(function (combo) {
          if (combo.$checkValidity) {
            combo.checkValidity = combo.$checkValidity;
            delete combo.$checkValidity;
          }
          if (combo.$validate) {
            combo.validate = combo.$validate;
            delete combo.$validate;
          }
        });

        const commitPage = tryCatchWrapper(function (page, callback) {
          let data = cache[page];

          if (comboBox._clientSideFilter) {
            performClientSideFilter(data, comboBox.filter, callback);
          } else {
            // Remove the data if server-side filtering, but keep it for client-side
            // filtering
            delete cache[page];

            // FIXME: It may be that we ought to provide data.length instead of
            // comboBox.size and remove updateSize function.
            callback(data, comboBox.size);
          }
        });

        // Perform filter on client side (here) using the items from specified page
        // and submitting the filtered items to specified callback.
        // The filter used is the one from combobox, not the lastFilter stored since
        // that may not reflect user's input.
        const performClientSideFilter = tryCatchWrapper(function (page, filter, callback) {
          let filteredItems = page;

          if (filter) {
            filteredItems = page.filter((item) => comboBox.$connector.filter(item, filter));
          }

          callback(filteredItems, filteredItems.length);
        });

        // Prevent setting the custom value as the 'value'-prop automatically
        comboBox.addEventListener(
          'custom-value-set',
          tryCatchWrapper((e) => e.preventDefault())
        );
      })(comboBox)
  };
})();

window.Vaadin.ComboBoxPlaceholder = ComboBoxPlaceholder;
