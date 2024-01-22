package com.vaadin.flow.component.charts.model;

/*-
 * #%L
 * Vaadin Charts for Flow
 * %%
 * Copyright (C) 2014 - 2019 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <https://vaadin.com/license/cval-3>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.time.Instant;
import com.vaadin.flow.component.charts.util.Util;

/**
 * The column range is a cartesian series type with higher and lower Y values
 * along an X axis. Requires <code>highcharts-more.js</code>. To display
 * horizontal bars, set <a href="#chart.inverted">chart.inverted</a> to
 * <code>true</code>.
 */
public class PlotOptionsColumnrange extends ColumnOptions {

    private Boolean allowPointSelect;
    private Boolean animation;
    private Number animationLimit;
    private Number borderRadius;
    private String className;
    private Boolean colorByPoint;
    private Number colorIndex;
    private Boolean crisp;
    private Number cropThreshold;
    private Cursor cursor;
    private DataLabelsRange dataLabels;
    private Number depth;
    private String description;
    private Number edgeWidth;
    private Boolean enableMouseTracking;
    private Boolean exposeElementToA11y;
    private Dimension findNearestPointBy;
    private Boolean getExtremesFromAll;
    private Number groupPadding;
    private Number groupZPadding;
    private Boolean grouping;
    private ArrayList<String> keys;
    private String linkedTo;
    private Number maxPointWidth;
    private Number minPointLength;
    private String _fn_pointDescriptionFormatter;
    private Number pointInterval;
    private IntervalUnit pointIntervalUnit;
    private Number pointPadding;
    private PointPlacement pointPlacement;
    private Number pointRange;
    private Number pointStart;
    private Number pointWidth;
    private Boolean selected;
    private Boolean shadow;
    private Boolean showCheckbox;
    private Boolean showInLegend;
    private Boolean skipKeyboardNavigation;
    private States states;
    private Boolean stickyTracking;
    private SeriesTooltip tooltip;
    private Number turboThreshold;
    private Boolean visible;
    private ZoneAxis zoneAxis;
    private ArrayList<Zones> zones;
    private Compare compare;
    private Number compareBase;
    private DataGrouping dataGrouping;
    private String gapUnit;
    private Number legendIndex;
    private PlotOptionsSeries navigatorOptions;
    private Boolean showInNavigator;

    public PlotOptionsColumnrange() {
    }

    @Override
    public ChartType getChartType() {
        return ChartType.COLUMNRANGE;
    }

    /**
     * @see #setAllowPointSelect(Boolean)
     */
    public Boolean getAllowPointSelect() {
        return allowPointSelect;
    }

    /**
     * Allow this series' points to be selected by clicking on the markers, bars
     * or pie slices.
     * <p>
     * Defaults to: false
     */
    public void setAllowPointSelect(Boolean allowPointSelect) {
        this.allowPointSelect = allowPointSelect;
    }

    /**
     * @see #setAnimation(Boolean)
     */
    public Boolean getAnimation() {
        return animation;
    }

    /**
     * Enable or disable the initial animation when a series is displayed.
     * Please note that this option only applies to the initial animation of the
     * series itself. For other animations, see
     * {@link ChartModel#setAnimation(Boolean)}
     */
    public void setAnimation(Boolean animation) {
        this.animation = animation;
    }

    /**
     * @see #setAnimationLimit(Number)
     */
    public Number getAnimationLimit() {
        return animationLimit;
    }

    /**
     * For some series, there is a limit that shuts down initial animation by
     * default when the total number of points in the chart is too high. For
     * example, for a column chart and its derivatives, animation doesn't run if
     * there is more than 250 points totally. To disable this cap, set
     * <code>animationLimit</code> to <code>Infinity</code>.
     */
    public void setAnimationLimit(Number animationLimit) {
        this.animationLimit = animationLimit;
    }

    /**
     * @see #setBorderRadius(Number)
     */
    public Number getBorderRadius() {
        return borderRadius;
    }

    /**
     * The corner radius of the border surrounding each column or bar.
     * <p>
     * Defaults to: 0
     */
    public void setBorderRadius(Number borderRadius) {
        this.borderRadius = borderRadius;
    }

    /**
     * @see #setClassName(String)
     */
    public String getClassName() {
        return className;
    }

    /**
     * A class name to apply to the series' graphical elements.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @see #setColorByPoint(Boolean)
     */
    public Boolean getColorByPoint() {
        return colorByPoint;
    }

    /**
     * When using automatic point colors pulled from the
     * <code>options.colors</code> collection, this option determines whether
     * the chart should receive one color per series or one color per point.
     * <p>
     * Defaults to: false
     */
    public void setColorByPoint(Boolean colorByPoint) {
        this.colorByPoint = colorByPoint;
    }

    /**
     * @see #setColorIndex(Number)
     */
    public Number getColorIndex() {
        return colorIndex;
    }

    /**
     * <a href=
     * "http://www.highcharts.com/docs/chart-design-and-style/style-by-css"
     * >Styled mode</a> only. A specific color index to use for the series, so
     * its graphic representations are given the class name
     * <code>highcharts-color-{n}</code>.
     */
    public void setColorIndex(Number colorIndex) {
        this.colorIndex = colorIndex;
    }

    /**
     * @see #setCrisp(Boolean)
     */
    public Boolean getCrisp() {
        return crisp;
    }

    /**
     * When true, each column edge is rounded to its nearest pixel in order to
     * render sharp on screen. In some cases, when there are a lot of densely
     * packed columns, this leads to visible difference in column widths or
     * distance between columns. In these cases, setting <code>crisp</code> to
     * <code>false</code> may look better, even though each column is rendered
     * blurry.
     * <p>
     * Defaults to: true
     */
    public void setCrisp(Boolean crisp) {
        this.crisp = crisp;
    }

    /**
     * @see #setCropThreshold(Number)
     */
    public Number getCropThreshold() {
        return cropThreshold;
    }

    /**
     * When the series contains less points than the crop threshold, all points
     * are drawn, event if the points fall outside the visible plot area at the
     * current zoom. The advantage of drawing all points (including markers and
     * columns), is that animation is performed on updates. On the other hand,
     * when the series contains more points than the crop threshold, the series
     * data is cropped to only contain points that fall within the plot area.
     * The advantage of cropping away invisible points is to increase
     * performance on large series. .
     * <p>
     * Defaults to: 50
     */
    public void setCropThreshold(Number cropThreshold) {
        this.cropThreshold = cropThreshold;
    }

    /**
     * @see #setCursor(Cursor)
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * You can set the cursor to "pointer" if you have click events attached to
     * the series, to signal to the user that the points and lines can be
     * clicked.
     */
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    /**
     * @see #setDataLabels(DataLabelsRange)
     */
    public DataLabelsRange getDataLabels() {
        if (dataLabels == null) {
            dataLabels = new DataLabelsRange();
        }
        return dataLabels;
    }

    /**
     * Extended data labels for range series types. Range series data labels
     * have no <code>x</code> and <code>y</code> options. Instead, they have
     * <code>xLow</code>, <code>xHigh</code>, <code>yLow</code> and
     * <code>yHigh</code> options to allow the higher and lower data label sets
     * individually.
     */
    public void setDataLabels(DataLabelsRange dataLabels) {
        this.dataLabels = dataLabels;
    }

    /**
     * @see #setDepth(Number)
     */
    public Number getDepth() {
        return depth;
    }

    /**
     * Depth of the columns in a 3D column chart. Requires
     * <code>highcharts-3d.js</code>.
     * <p>
     * Defaults to: 25
     */
    public void setDepth(Number depth) {
        this.depth = depth;
    }

    /**
     * @see #setDescription(String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * <i>Requires Accessibility module</i>
     * </p>
     * <p>
     * A description of the series to add to the screen reader information about
     * the series.
     * </p>
     * <p>
     * Defaults to: undefined
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @see #setEdgeWidth(Number)
     */
    public Number getEdgeWidth() {
        return edgeWidth;
    }

    /**
     * 3D columns only. The width of the colored edges.
     * <p>
     * Defaults to: 1
     */
    public void setEdgeWidth(Number edgeWidth) {
        this.edgeWidth = edgeWidth;
    }

    /**
     * @see #setEnableMouseTracking(Boolean)
     */
    public Boolean getEnableMouseTracking() {
        return enableMouseTracking;
    }

    /**
     * Enable or disable the mouse tracking for a specific series. This includes
     * point tooltips and click events on graphs and points. For large datasets
     * it improves performance.
     * <p>
     * Defaults to: true
     */
    public void setEnableMouseTracking(Boolean enableMouseTracking) {
        this.enableMouseTracking = enableMouseTracking;
    }

    /**
     * @see #setExposeElementToA11y(Boolean)
     */
    public Boolean getExposeElementToA11y() {
        return exposeElementToA11y;
    }

    /**
     * <p>
     * By default, series are exposed to screen readers as regions. By enabling
     * this option, the series element itself will be exposed in the same way as
     * the data points. This is useful if the series is not used as a grouping
     * entity in the chart, but you still want to attach a description to the
     * series.
     * </p>
     * <p>
     * Requires the Accessibility module.
     * </p>
     * <p>
     * Defaults to: undefined
     */
    public void setExposeElementToA11y(Boolean exposeElementToA11y) {
        this.exposeElementToA11y = exposeElementToA11y;
    }

    /**
     * @see #setFindNearestPointBy(Dimension)
     */
    public Dimension getFindNearestPointBy() {
        return findNearestPointBy;
    }

    /**
     * <p>
     * Determines whether the series should look for the nearest point in both
     * dimensions or just the x-dimension when hovering the series. Defaults to
     * <code>'xy'</code> for scatter series and <code>'x'</code> for most other
     * series. If the data has duplicate x-values, it is recommended to set this
     * to <code>'xy'</code> to allow hovering over all points.
     * </p>
     * <p>
     * Applies only to series types using nearest neighbor search (not direct
     * hover) for tooltip.
     * </p>
     */
    public void setFindNearestPointBy(Dimension findNearestPointBy) {
        this.findNearestPointBy = findNearestPointBy;
    }

    /**
     * @see #setGetExtremesFromAll(Boolean)
     */
    public Boolean getGetExtremesFromAll() {
        return getExtremesFromAll;
    }

    /**
     * Whether to use the Y extremes of the total chart width or only the zoomed
     * area when zooming in on parts of the X axis. By default, the Y axis
     * adjusts to the min and max of the visible data. Cartesian series only.
     * <p>
     * Defaults to: false
     */
    public void setGetExtremesFromAll(Boolean getExtremesFromAll) {
        this.getExtremesFromAll = getExtremesFromAll;
    }

    /**
     * @see #setGroupPadding(Number)
     */
    public Number getGroupPadding() {
        return groupPadding;
    }

    /**
     * Padding between each value groups, in x axis units.
     * <p>
     * Defaults to: 0.2
     */
    public void setGroupPadding(Number groupPadding) {
        this.groupPadding = groupPadding;
    }

    /**
     * @see #setGroupZPadding(Number)
     */
    public Number getGroupZPadding() {
        return groupZPadding;
    }

    /**
     * The spacing between columns on the Z Axis in a 3D chart. Requires
     * <code>highcharts-3d.js</code>.
     * <p>
     * Defaults to: 1
     */
    public void setGroupZPadding(Number groupZPadding) {
        this.groupZPadding = groupZPadding;
    }

    /**
     * @see #setGrouping(Boolean)
     */
    public Boolean getGrouping() {
        return grouping;
    }

    /**
     * Whether to group non-stacked columns or to let them render independent of
     * each other. Non-grouped columns will be laid out individually and overlap
     * each other.
     * <p>
     * Defaults to: true
     */
    public void setGrouping(Boolean grouping) {
        this.grouping = grouping;
    }

    /**
     * @see #setKeys(String...)
     */
    public String[] getKeys() {
        if (keys == null) {
            return new String[] {};
        }
        String[] arr = new String[keys.size()];
        keys.toArray(arr);
        return arr;
    }

    /**
     * An array specifying which option maps to which key in the data point
     * array. This makes it convenient to work with unstructured data arrays
     * from different sources.
     */
    public void setKeys(String... keys) {
        this.keys = new ArrayList<String>(Arrays.asList(keys));
    }

    /**
     * Adds key to the keys array
     *
     * @param key
     *            to add
     * @see #setKeys(String...)
     */
    public void addKey(String key) {
        if (this.keys == null) {
            this.keys = new ArrayList<String>();
        }
        this.keys.add(key);
    }

    /**
     * Removes first occurrence of key in keys array
     *
     * @param key
     *            to remove
     * @see #setKeys(String...)
     */
    public void removeKey(String key) {
        this.keys.remove(key);
    }

    /**
     * @see #setLinkedTo(String)
     */
    public String getLinkedTo() {
        return linkedTo;
    }

    /**
     * The <a href="#series.id">id</a> of another series to link to.
     * Additionally, the value can be ":previous" to link to the previous
     * series. When two series are linked, only the first one appears in the
     * legend. Toggling the visibility of this also toggles the linked series.
     */
    public void setLinkedTo(String linkedTo) {
        this.linkedTo = linkedTo;
    }

    /**
     * @see #setMaxPointWidth(Number)
     */
    public Number getMaxPointWidth() {
        return maxPointWidth;
    }

    /**
     * The maximum allowed pixel width for a column, translated to the height of
     * a bar in a bar chart. This prevents the columns from becoming too wide
     * when there is a small number of points in the chart.
     * <p>
     * Defaults to: null
     */
    public void setMaxPointWidth(Number maxPointWidth) {
        this.maxPointWidth = maxPointWidth;
    }

    /**
     * @see #setMinPointLength(Number)
     */
    public Number getMinPointLength() {
        return minPointLength;
    }

    /**
     * The minimal height for a column or width for a bar. By default, 0 values
     * are not shown. To visualize a 0 (or close to zero) point, set the minimal
     * point length to a pixel value like 3. In stacked column charts,
     * minPointLength might not be respected for tightly packed values.
     * <p>
     * Defaults to: 0
     */
    public void setMinPointLength(Number minPointLength) {
        this.minPointLength = minPointLength;
    }

    public String getPointDescriptionFormatter() {
        return _fn_pointDescriptionFormatter;
    }

    public void setPointDescriptionFormatter(
            String _fn_pointDescriptionFormatter) {
        this._fn_pointDescriptionFormatter = _fn_pointDescriptionFormatter;
    }

    /**
     * @see #setPointInterval(Number)
     */
    public Number getPointInterval() {
        return pointInterval;
    }

    /**
     * <p>
     * If no x values are given for the points in a series, pointInterval
     * defines the interval of the x values. For example, if a series contains
     * one value every decade starting from year 0, set pointInterval to 10.
     * </p>
     * <p>
     * Since Highcharts 4.1, it can be combined with
     * <code>pointIntervalUnit</code> to draw irregular intervals.
     * </p>
     * <p>
     * Defaults to: 1
     */
    public void setPointInterval(Number pointInterval) {
        this.pointInterval = pointInterval;
    }

    /**
     * @see #setPointIntervalUnit(IntervalUnit)
     */
    public IntervalUnit getPointIntervalUnit() {
        return pointIntervalUnit;
    }

    /**
     * On datetime series, this allows for setting the
     * <a href="#plotOptions.series.pointInterval">pointInterval</a> to
     * irregular time units, <code>day</code>, <code>month</code> and
     * <code>year</code>. A day is usually the same as 24 hours, but
     * pointIntervalUnit also takes the DST crossover into consideration when
     * dealing with local time. Combine this option with
     * <code>pointInterval</code> to draw weeks, quarters, 6 months, 10 years
     * etc.
     */
    public void setPointIntervalUnit(IntervalUnit pointIntervalUnit) {
        this.pointIntervalUnit = pointIntervalUnit;
    }

    /**
     * @see #setPointPadding(Number)
     */
    public Number getPointPadding() {
        return pointPadding;
    }

    /**
     * Padding between each column or bar, in x axis units.
     * <p>
     * Defaults to: 0.1
     */
    public void setPointPadding(Number pointPadding) {
        this.pointPadding = pointPadding;
    }

    /**
     * @see #setPointPlacement(PointPlacement)
     */
    public PointPlacement getPointPlacement() {
        return pointPlacement;
    }

    /**
     * <p>
     * Possible values: <code>null</code>, <code>"on"</code>,
     * <code>"between"</code>.
     * </p>
     * <p>
     * In a column chart, when pointPlacement is <code>"on"</code>, the point
     * will not create any padding of the X axis. In a polar column chart this
     * means that the first column points directly north. If the pointPlacement
     * is <code>"between"</code>, the columns will be laid out between ticks.
     * This is useful for example for visualising an amount between two points
     * in time or in a certain sector of a polar chart.
     * </p>
     * <p>
     * Since Highcharts 3.0.2, the point placement can also be numeric, where 0
     * is on the axis value, -0.5 is between this value and the previous, and
     * 0.5 is between this value and the next. Unlike the textual options,
     * numeric point placement options won't affect axis padding.
     * </p>
     * <p>
     * Note that pointPlacement needs a
     * <a href="#plotOptions.series.pointRange">pointRange</a> to work. For
     * column series this is computed, but for line-type series it needs to be
     * set.
     * </p>
     * <p>
     * Defaults to <code>null</code> in cartesian charts, <code>"between"</code>
     * in polar charts.
     */
    public void setPointPlacement(PointPlacement pointPlacement) {
        this.pointPlacement = pointPlacement;
    }

    /**
     * @see #setPointRange(Number)
     */
    public Number getPointRange() {
        return pointRange;
    }

    /**
     * The X axis range that each point is valid for. This determines the width
     * of the column. On a categorized axis, the range will be 1 by default (one
     * category unit). On linear and datetime axes, the range will be computed
     * as the distance between the two closest data points.
     */
    public void setPointRange(Number pointRange) {
        this.pointRange = pointRange;
    }

    /**
     * @see #setPointStart(Number)
     */
    public Number getPointStart() {
        return pointStart;
    }

    /**
     * If no x values are given for the points in a series, pointStart defines
     * on what value to start. For example, if a series contains one yearly
     * value starting from 1945, set pointStart to 1945.
     * <p>
     * Defaults to: 0
     */
    public void setPointStart(Number pointStart) {
        this.pointStart = pointStart;
    }

    /**
     * @see #setPointWidth(Number)
     */
    public Number getPointWidth() {
        return pointWidth;
    }

    /**
     * A pixel value specifying a fixed width for each column or bar. When
     * <code>null</code>, the width is calculated from the
     * <code>pointPadding</code> and <code>groupPadding</code>.
     */
    public void setPointWidth(Number pointWidth) {
        this.pointWidth = pointWidth;
    }

    /**
     * @see #setSelected(Boolean)
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Whether to select the series initially. If <code>showCheckbox</code> is
     * true, the checkbox next to the series name will be checked for a selected
     * series.
     * <p>
     * Defaults to: false
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * @see #setShadow(Boolean)
     */
    public Boolean getShadow() {
        return shadow;
    }

    /**
     * Whether to apply a drop shadow to the graph line. Since 2.3 the shadow
     * can be an object configuration containing <code>color</code>,
     * <code>offsetX</code>, <code>offsetY</code>, <code>opacity</code> and
     * <code>width</code>.
     * <p>
     * Defaults to: false
     */
    public void setShadow(Boolean shadow) {
        this.shadow = shadow;
    }

    /**
     * @see #setShowCheckbox(Boolean)
     */
    public Boolean getShowCheckbox() {
        return showCheckbox;
    }

    /**
     * If true, a checkbox is displayed next to the legend item to allow
     * selecting the series. The state of the checkbox is determined by the
     * <code>selected</code> option.
     * <p>
     * Defaults to: false
     */
    public void setShowCheckbox(Boolean showCheckbox) {
        this.showCheckbox = showCheckbox;
    }

    /**
     * @see #setShowInLegend(Boolean)
     */
    public Boolean getShowInLegend() {
        return showInLegend;
    }

    /**
     * Whether to display this particular series or series type in the legend.
     * The default value is <code>true</code> for standalone series,
     * <code>false</code> for linked series.
     * <p>
     * Defaults to: true
     */
    public void setShowInLegend(Boolean showInLegend) {
        this.showInLegend = showInLegend;
    }

    /**
     * @see #setSkipKeyboardNavigation(Boolean)
     */
    public Boolean getSkipKeyboardNavigation() {
        return skipKeyboardNavigation;
    }

    /**
     * If set to <code>True</code>, the accessibility module will skip past the
     * points in this series for keyboard navigation.
     */
    public void setSkipKeyboardNavigation(Boolean skipKeyboardNavigation) {
        this.skipKeyboardNavigation = skipKeyboardNavigation;
    }

    /**
     * @see #setStates(States)
     */
    public States getStates() {
        if (states == null) {
            states = new States();
        }
        return states;
    }

    /**
     * A wrapper object for all the series options in specific states.
     */
    public void setStates(States states) {
        this.states = states;
    }

    /**
     * @see #setStickyTracking(Boolean)
     */
    public Boolean getStickyTracking() {
        return stickyTracking;
    }

    /**
     * Sticky tracking of mouse events. When true, the <code>mouseOut</code>
     * event on a series isn't triggered until the mouse moves over another
     * series, or out of the plot area. When false, the <code>mouseOut</code>
     * event on a series is triggered when the mouse leaves the area around the
     * series' graph or markers. This also implies the tooltip. When
     * <code>stickyTracking</code> is false and <code>tooltip.shared</code> is
     * false, the tooltip will be hidden when moving the mouse between series.
     * Defaults to true for line and area type series, but to false for columns,
     * pies etc.
     * <p>
     * Defaults to: true
     */
    public void setStickyTracking(Boolean stickyTracking) {
        this.stickyTracking = stickyTracking;
    }

    /**
     * @see #setTooltip(SeriesTooltip)
     */
    public SeriesTooltip getTooltip() {
        if (tooltip == null) {
            tooltip = new SeriesTooltip();
        }
        return tooltip;
    }

    /**
     * A configuration object for the tooltip rendering of each single series.
     * Properties are inherited from <a href="#tooltip">tooltip</a>, but only
     * the following properties can be defined on a series level.
     */
    public void setTooltip(SeriesTooltip tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * @see #setTurboThreshold(Number)
     */
    public Number getTurboThreshold() {
        return turboThreshold;
    }

    /**
     * When a series contains a data array that is longer than this, only one
     * dimensional arrays of numbers, or two dimensional arrays with x and y
     * values are allowed. Also, only the first point is tested, and the rest
     * are assumed to be the same format. This saves expensive data checking and
     * indexing in long series. Set it to <code>0</code> disable.
     * <p>
     * Defaults to: 1000
     */
    public void setTurboThreshold(Number turboThreshold) {
        this.turboThreshold = turboThreshold;
    }

    /**
     * @see #setVisible(Boolean)
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * Set the initial visibility of the series.
     * <p>
     * Defaults to: true
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * @see #setZoneAxis(ZoneAxis)
     */
    public ZoneAxis getZoneAxis() {
        return zoneAxis;
    }

    /**
     * Defines the Axis on which the zones are applied.
     * <p>
     * Defaults to: y
     */
    public void setZoneAxis(ZoneAxis zoneAxis) {
        this.zoneAxis = zoneAxis;
    }

    /**
     * @see #setZones(Zones...)
     */
    public Zones[] getZones() {
        if (zones == null) {
            return new Zones[] {};
        }
        Zones[] arr = new Zones[zones.size()];
        zones.toArray(arr);
        return arr;
    }

    /**
     * <p>
     * An array defining zones within a series. Zones can be applied to the X
     * axis, Y axis or Z axis for bubbles, according to the
     * <code>zoneAxis</code> option.
     * </p>
     *
     * <p>
     * In <a href=
     * "http://www.highcharts.com/docs/chart-design-and-style/style-by-css"
     * >styled mode</a>, the color zones are styled with the
     * <code>.highcharts-zone-{n}</code> class, or custom classed from the
     * <code>className</code> option (<a href=
     * "http://jsfiddle.net/gh/get/library/pure/highcharts/highcharts/tree/master/samples/highcharts/css/color-zones/"
     * >view live demo</a>).
     * </p>
     */
    public void setZones(Zones... zones) {
        this.zones = new ArrayList<Zones>(Arrays.asList(zones));
    }

    /**
     * Adds zone to the zones array
     *
     * @param zone
     *            to add
     * @see #setZones(Zones...)
     */
    public void addZone(Zones zone) {
        if (this.zones == null) {
            this.zones = new ArrayList<Zones>();
        }
        this.zones.add(zone);
    }

    /**
     * Removes first occurrence of zone in zones array
     *
     * @param zone
     *            to remove
     * @see #setZones(Zones...)
     */
    public void removeZone(Zones zone) {
        this.zones.remove(zone);
    }

    /**
     * @see #setCompare(Compare)
     */
    public Compare getCompare() {
        return compare;
    }

    /**
     * Compare the values of the series against the first non-null, non-zero
     * value in the visible range. The y axis will show percentage or absolute
     * change depending on whether <code>compare</code> is set to
     * <code>"percent"</code> or <code>"value"</code>. When this is applied to
     * multiple series, it allows comparing the development of the series
     * against each other.
     * <p>
     * Defaults to: undefined
     */
    public void setCompare(Compare compare) {
        this.compare = compare;
    }

    /**
     * @see #setCompareBase(Number)
     */
    public Number getCompareBase() {
        return compareBase;
    }

    /**
     * When <a href="#plotOptions.series.compare">compare</a> is
     * <code>percent</code>, this option dictates whether to use 0 or 100 as the
     * base of comparison.
     * <p>
     * Defaults to: 0
     */
    public void setCompareBase(Number compareBase) {
        this.compareBase = compareBase;
    }

    /**
     * @see #setDataGrouping(DataGrouping)
     */
    public DataGrouping getDataGrouping() {
        if (dataGrouping == null) {
            dataGrouping = new DataGrouping();
        }
        return dataGrouping;
    }

    public void setDataGrouping(DataGrouping dataGrouping) {
        this.dataGrouping = dataGrouping;
    }

    /**
     * @see #setGapUnit(String)
     */
    public String getGapUnit() {
        return gapUnit;
    }

    /**
     * Together with <code>gapSize</code>, this option defines where to draw
     * gaps in the graph.
     * <p>
     * Defaults to: relative
     */
    public void setGapUnit(String gapUnit) {
        this.gapUnit = gapUnit;
    }

    /**
     * @see #setLegendIndex(Number)
     */
    public Number getLegendIndex() {
        return legendIndex;
    }

    /**
     * The sequential index of the series within the legend.
     * <p>
     * Defaults to: 0
     */
    public void setLegendIndex(Number legendIndex) {
        this.legendIndex = legendIndex;
    }

    /**
     * @see #setNavigatorOptions(PlotOptionsSeries)
     */
    public PlotOptionsSeries getNavigatorOptions() {
        return navigatorOptions;
    }

    /**
     * <p>
     * Options for the corresponding navigator series if
     * <code>showInNavigator</code> is <code>true</code> for this series.
     * Available options are the same as any series, documented at
     * <a class="internal" href="#plotOptions.series">plotOptions</a> and
     * <a class="internal" href="#series">series</a>.
     * </p>
     *
     * <p>
     * These options are merged with options in
     * <a href="#navigator.series">navigator.series</a>, and will take
     * precedence if the same option is defined both places.
     * </p>
     * <p>
     * Defaults to: undefined
     */
    public void setNavigatorOptions(PlotOptionsSeries navigatorOptions) {
        this.navigatorOptions = navigatorOptions;
    }

    /**
     * @see #setShowInNavigator(Boolean)
     */
    public Boolean getShowInNavigator() {
        return showInNavigator;
    }

    /**
     * Whether or not to show the series in the navigator. Takes precedence over
     * <a href="#navigator.baseSeries">navigator.baseSeries</a> if defined.
     * <p>
     * Defaults to: undefined
     */
    public void setShowInNavigator(Boolean showInNavigator) {
        this.showInNavigator = showInNavigator;
    }

    /**
     * @deprecated as of 4.0. Use {@link #setPointStart(Instant)}
     */
    @Deprecated
    public void setPointStart(Date date) {
        this.pointStart = Util.toHighchartsTS(date);
    }

    /**
     * @see #setPointStart(Number)
     */
    public void setPointStart(Instant instant) {
        this.pointStart = Util.toHighchartsTS(instant);
    }
}
