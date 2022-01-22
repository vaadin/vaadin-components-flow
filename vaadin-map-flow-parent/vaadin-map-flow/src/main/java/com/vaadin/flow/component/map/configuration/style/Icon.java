package com.vaadin.flow.component.map.configuration.style;

/*
 * #%L
 * Vaadin Map
 * %%
 * Copyright (C) 2022 - 2022 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Developer License
 * 4.0 (CVDLv4).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * For the full License, see <https://vaadin.com/license/cvdl-4.0>.
 * #L%
 */

import com.vaadin.flow.component.map.configuration.Constants;

public class Icon extends ImageStyle {

    private Anchor anchor;
    private final AnchorOrigin anchorOrigin;
    private final String color;
    private final String crossOrigin;
    private final String src;
    private final String imgName;
    private final ImageSize imgSize;

    @Override
    public String getType() {
        return Constants.OL_STYLE_ICON;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
        notifyChange();
    }

    public AnchorOrigin getAnchorOrigin() {
        return anchorOrigin;
    }

    public String getColor() {
        return color;
    }

    public String getCrossOrigin() {
        return crossOrigin;
    }

    public String getSrc() {
        return src;
    }

    public String getImgName() {
        return imgName;
    }

    public ImageSize getImgSize() {
        return imgSize;
    }

    public Icon(Options options) {
        super(options);
        anchor = options.anchor;
        anchorOrigin = options.anchorOrigin;
        color = options.color;
        crossOrigin = options.crossOrigin;
        src = options.src;
        imgName = options.imgName;
        imgSize = options.imgSize;
    }

    public static class Options extends ImageStyle.BaseOptions<Options> {
        private Anchor anchor = new Anchor();
        private AnchorOrigin anchorOrigin = AnchorOrigin.TOP_LEFT;
        private String color;
        private String crossOrigin;
        private String src;
        private String imgName;
        private ImageSize imgSize;

        public Options setAnchor(Anchor anchor) {
            this.anchor = anchor;
            return getThis();
        }

        public Options setAnchorOrigin(AnchorOrigin anchorOrigin) {
            this.anchorOrigin = anchorOrigin;
            return getThis();
        }

        public Options setColor(String color) {
            this.color = color;
            return getThis();
        }

        public Options setCrossOrigin(String crossOrigin) {
            this.crossOrigin = crossOrigin;
            return getThis();
        }

        public Options setSrc(String src) {
            this.src = src;
            return getThis();
        }

        public Options setImgName(String imgName) {
            this.imgName = imgName;
            return getThis();
        }

        public Options setImgSize(ImageSize imgSize) {
            this.imgSize = imgSize;
            return getThis();
        }
    }

    public static class Anchor {
        private float x = 0.5f;
        private float y = 0.5f;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public Anchor() {
        }

        public Anchor(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum AnchorOrigin {
        BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT,
    }

    public static class ImageSize {
        int width;
        int height;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
