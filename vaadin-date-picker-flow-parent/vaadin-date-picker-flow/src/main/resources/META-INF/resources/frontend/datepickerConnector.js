import dateFnsFormat from 'date-fns/format';
import dateFnsParse from 'date-fns/parse';
import dateFnsIsValid from 'date-fns/isValid';
import { extractDateParts, getAdjustedYear } from '@vaadin/date-picker/src/vaadin-date-picker-helper';

(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Date Picker');
  };

  window.Vaadin.Flow.datepickerConnector = {
    initLazy: (datepicker) =>
      tryCatchWrapper(function (datepicker) {
        // Check whether the connector was already initialized for the datepicker
        if (datepicker.$connector) {
          return;
        }

        datepicker.$connector = {};

        datepicker.addEventListener(
          'blur',
          tryCatchWrapper((e) => {
            if (!e.target.value && e.target.invalid) {
              console.warn('Invalid value in the DatePicker.');
            }
          })
        );

        const createLocaleBasedDateFormat = function (locale) {
          try {
            // Check whether the locale is supported or not
            new Date().toLocaleDateString(locale);
          } catch (e) {
            console.warn('The locale is not supported, using default locale setting(en-US).');
            return 'M/d/y';
          }

          // format test date and convert to date-fns pattern
          const testDate = new Date(Date.UTC(1234, 4, 6));
          let pattern = testDate.toLocaleDateString(locale, { timeZone: 'UTC' });
          pattern = pattern
            // escape date-fns pattern letters by enclosing them in single quotes
            .replace(/([a-zA-Z]+)/g, "'$1'")
            // insert date placeholder
            .replace('06', 'dd')
            .replace('6', 'd')
            // insert month placeholder
            .replace('05', 'MM')
            .replace('5', 'M')
            // insert year placeholder
            .replace('1234', 'y');
          const isValidPattern = pattern.includes('d') && pattern.includes('M') && pattern.includes('y');
          if (!isValidPattern) {
            console.warn('The locale is not supported, using default locale setting(en-US).');
            return 'M/d/y';
          }

          return pattern;
        };

        const createFormatterAndParser = tryCatchWrapper(function (formats) {
          if (!formats || formats.length === 0) {
            throw new Error('Array of custom date formats is null or empty');
          }

          function _shortenFourDigitYearFormat(format) {
            if (format.includes('yyyy') && !format.includes('yyyyy')) {
              return format.replace('yyyy', 'yy');
            }
            if (format.includes('YYYY') && !format.includes('YYYYY')) {
              return format.replace('YYYY', 'YY');
            }
            return undefined;
          }

          function _isShortFormat(format) {
            if (format.includes('y')) {
              return !format.includes('yyy');
            }
            if (format.includes('Y')) {
              return !format.includes('YYY');
            }
            return false;
          }

          function formatDate(dateParts) {
            const format = formats[0];
            const date = datepicker._parseDate(`${dateParts.year}-${dateParts.month + 1}-${dateParts.day}`);

            return dateFnsFormat(date, format);
          }

          function parseDate(dateString) {
            const referenceDate = _getReferenceDate();
            for (let format of formats) {
              // We first try to match the date with the shorter version.
              const shorterFormat = _shortenFourDigitYearFormat(format);
              if (shorterFormat) {
                const shorterFormatDate = dateFnsParse(dateString, shorterFormat, referenceDate);
                if (dateFnsIsValid(shorterFormatDate)) {
                  let yearValue = shorterFormatDate.getFullYear();
                  // The last parsed year check handles the case when a date with an actual year value is provided
                  // with zero padding, but then got reformatted without the zeroes and parsed again.
                  if (this._lastParsedYear && yearValue == this._lastParsedYear % 100) {
                    yearValue = this._lastParsedYear;
                  }
                  return {
                    day: shorterFormatDate.getDate(),
                    month: shorterFormatDate.getMonth(),
                    year: yearValue
                  };
                }
              }
              const date = dateFnsParse(dateString, format, referenceDate);

              if (dateFnsIsValid(date)) {
                let yearValue = date.getFullYear();
                if (this._lastParsedYear && yearValue % 100 == this._lastParsedYear % 100 && _isShortFormat(format)) {
                  yearValue = this._lastParsedYear;
                } else {
                  this._lastParsedYear = yearValue;
                }
                return {
                  day: date.getDate(),
                  month: date.getMonth(),
                  year: yearValue
                };
              }
            }
            this._lastParsedYear = undefined;
            return false;
          }

          return {
            formatDate: formatDate,
            parseDate: parseDate
          };
        });

        function _getReferenceDate() {
          return datepicker.i18n.referenceDate ? new Date(datepicker.i18n.referenceDate.year,
              datepicker.i18n.referenceDate.month - 1, datepicker.i18n.referenceDate.day) : new Date();
        }

        datepicker.$connector.updateI18n = tryCatchWrapper(function (locale, i18n) {
          // Either use custom formats specified in I18N, or create format from locale
          const hasCustomFormats = i18n && i18n.dateFormats && i18n.dateFormats.length > 0;
          if (i18n && i18n.referenceDate) {
            i18n.referenceDate = extractDateParts(new Date(i18n.referenceDate));
          }
          const usedFormats = hasCustomFormats ? i18n.dateFormats : [createLocaleBasedDateFormat(locale)];
          const formatterAndParser = createFormatterAndParser(usedFormats);

          // Merge current web component I18N settings with new I18N settings and the formatting and parsing functions
          datepicker.i18n = Object.assign({}, datepicker.i18n, i18n, formatterAndParser);
        });
      })(datepicker)
  };
})();
