package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapMarkerCirclePrettier extends MapMarkerCircle {
    private BufferedImage miniImage;
    private String userProfileImageURL;
    private String userName;
    private String tweet;

    public MapMarkerCirclePrettier(Layer layer, Coordinate coord, Color color, BufferedImage miniImage, String userProfileImageURL, String userName, String tweet) {
        super(layer, null, coord, 40.0, STYLE.FIXED, getDefaultStyle());
        this.miniImage = miniImage;
        this.userProfileImageURL = userProfileImageURL;
        this.userName = userName;
        this.tweet = tweet;
        setColor(Color.BLACK);
        setBackColor(color);
    }

    // getters
    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public String getTweet() {
        return tweet;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void paint(Graphics g, Point position, int radius) {
        int imgSize = radius - 20;
        if (g instanceof Graphics2D && this.getBackColor() != null) {
            Graphics2D g2 = (Graphics2D) g;
            Composite oldComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(3));
            g2.setPaint(this.getBackColor());
            g.fillOval(position.x - radius, position.y - radius, radius, radius);
            g.drawImage(miniImage, position.x - 30, position.y - 30, imgSize, imgSize,
                    Color.black, null);
            g2.setComposite(oldComposite);
        }
        g.setColor(this.getColor());
        g.drawOval(position.x - radius, position.y - radius, radius, radius);

        if (this.getLayer() == null || this.getLayer().isVisibleTexts()) {
            this.paintText(g, position);
        }
    }
}