import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {


    private ArrayList<Circle> circlesList;

    public GraphicsPanel() {
        super();
        this.setBackground(Color.RED);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (Circle tr : MyTable.getTardisModel().getCircles()) {
            g2.setColor(tr.getColor());
            g2.fill(tr);
        }
    }
}
