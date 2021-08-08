package query;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import twitter4j.Status;
import filters.Filter;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import ui.MapMarkerCirclePrettier;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * A query over the twitter stream.
 */
public class Query implements Observer {
    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
    }

    // getters
    public Color getColor() {
        return color;
    }
    public String getQueryString() {
        return queryString;
    }
    public Filter getFilter() {
        return filter;
    }
    public Layer getLayer() {
        return layer;
    }
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    public boolean getVisible() {
        return layer.isVisible();
    }

    // setters
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    // MODIFIES: this
    // EFFECTS: makes queries layer invisible and set layer to null
    public void terminate() {
        this.layer.setVisible(false);
        this.layer = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        Status tweetData = (Status) arg;
        Coordinate coordinate = Util.statusCoordinate(tweetData);
        String tweetText = tweetData.getText();
        String userMiniImageURL = tweetData.getUser().getMiniProfileImageURL();
        String userProfileImageURL = tweetData.getUser().getProfileImageURL();
        String userName = tweetData.getUser().getName();
        BufferedImage userMiniImage = Util.imageFromURL(userMiniImageURL);

        MapMarkerCirclePrettier marker = new MapMarkerCirclePrettier(layer, coordinate, color, userMiniImage, userProfileImageURL, userName, tweetData.getText());

        if (filter.matches(tweetData)) {
            map.addMapMarker(marker);
            System.out.println("for query: " + this + " found " + tweetText);
        }
    }
}
