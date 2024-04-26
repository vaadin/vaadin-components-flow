/**
 * @license
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
import ImageLayer from 'ol/layer/Image';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import { createOptions } from './util.js';
import { Circle as CircleStyle, Fill, Stroke, Style, Text } from 'ol/style';

function synchronizeLayer(target, source, _context) {
  if (!target) {
    throw new Error('Can not instantiate base class: ol/layer/Layer');
  }

  target.setOpacity(source.opacity);
  target.setVisible(source.visible);
  target.setZIndex(source.zIndex || undefined);
  target.setMinZoom(source.minZoom || -Infinity);
  target.setMaxZoom(source.maxZoom || Infinity);
  target.setBackground(source.background || undefined);

  return target;
}

export function synchronizeTileLayer(target, source, context) {
  if (!target) {
    target = new TileLayer(
      createOptions({
        ...source,
        source: context.lookup.get(source.source)
      })
    );
  }

  synchronizeLayer(target, source);
  target.setSource(context.lookup.get(source.source));

  return target;
}

export function synchronizeVectorLayer(target, source, context) {
  if (!target) {
    target = new VectorLayer(
      createOptions({
        ...source,
        source: context.lookup.get(source.source),
        // TODO: This is just a quick hack, this should only be applied to cluster layers somehow, and be configurable
        style: function (feature) {
          const features = feature.get('features');
          const size = features.length;
          // When rendering a single feature, use the feature's style
          if (size === 1) {
            const feature = features[0];
            return feature.getStyleFunction()();
          }
          // Multiple features indicate a cluster, render a cluster style
          const backgroundCircle = new Style({
            image: new CircleStyle({
              radius: 19,
              stroke: new Stroke({
                color: '#00000022',
                width: 2
              }),
            })
          });
          const foregroundCircle = new Style({
            image: new CircleStyle({
              radius: 18,
              stroke: new Stroke({
                color: '#fff',
                width: 2
              }),
              fill: new Fill({
                color: '#1676f3'
              })
            }),
            text: new Text({
              text: size.toString(),
              font: 'bold 13px sans-serif',
              fill: new Fill({
                color: '#fff'
              })
            })
          });
          return [backgroundCircle, foregroundCircle];
        }
      })
    );
  }

  synchronizeLayer(target, source);
  target.setSource(context.lookup.get(source.source));

  return target;
}

export function synchronizeImageLayer(target, source, context) {
  if (!target) {
    target = new ImageLayer(
      createOptions({
        ...source,
        source: context.lookup.get(source.source)
      })
    );
  }

  synchronizeLayer(target, source);
  target.setSource(context.lookup.get(source.source));

  return target;
}
