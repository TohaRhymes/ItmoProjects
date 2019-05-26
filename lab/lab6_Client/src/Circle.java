import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Circle extends Ellipse2D {

    private Point point;

    @Setter
    private double width;
    @Setter
    private double height;
    @Setter
    @Getter
    private Color color;
    @Getter
    private int travellerNumber;

    public Circle(double x, double y, double width, double height, Color color, int travellerNumber) {
        point = new Point(x, y);
        this.width = width;
        this.height = height;
        this.color = new Color(
                (color.getRed() | 128) % 256,
                (color.getGreen() | 64) % 256,
                (color.getBlue() | 192) % 256
        );
//        this.color = new Color(
//                color.getRed() > color.getGreen() ? (color.getRed() > color.getBlue() ? 230 : color.getRed()) : (color.getRed() < color.getBlue() ? (color.getRed() * color.getRed())%255 : color.getRed()),
//                color.getGreen() > color.getBlue() ? (color.getGreen() > color.getRed() ? 230 : color.getGreen()) : (color.getGreen() < color.getRed() ? (color.getGreen() * color.getGreen())%255 : color.getGreen()),
//                color.getBlue() > color.getGreen() ? (color.getBlue() > color.getRed() ? 230 : color.getBlue()) : (color.getBlue() < color.getRed() ? (color.getBlue() * color.getBlue())%255 : color.getBlue())
//                );
        this.travellerNumber = travellerNumber;
    }

    public Circle(double x, double y, double width, double height, int travellerNumber) {
        point = new Point(x, y);
        this.width = width;
        this.height = height;
        this.travellerNumber = travellerNumber;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }


    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double width, double height) {
        point.setLocation(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {

        double dx = x - (this.getX() + this.getWidth());
        double dy = y - (this.getY() + this.getHeight());
        double sqr = Math.sqrt(dx * dx + dy * dy);

        return sqr <= this.getWidth();
    }


    @Override
    public boolean contains(Point2D p) {
        return this.contains(p.getX(), p.getY());
    }
}
