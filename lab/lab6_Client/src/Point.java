
import lombok.Getter;

import java.awt.geom.Point2D;

public class Point extends Point2D {

    @Getter private double x;
    @Getter private double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
