import javax.swing.*;
import java.awt.*;

public class ChangePanel extends Component {

    // TODO: Что делать с именем?
    private JButton changeName;
    private JButton changeBirthdayDate;
    private JButton changeLocalDate;
    private JButton changeX;
    private JButton changeY;
    private JButton changeAge;

    private JButton remove;

    private JButton create;

    private static JTextField nameField;
    private static JTextField BirthdayDateField;
    private static JTextField localDateField;
    private static JTextField xField;
    private static JTextField yField;
    private static JTextField ageField;

    private JPanel changePanel;

    public ChangePanel() {
        changeName = new JButton("changeName");
        changeName.setPreferredSize(new Dimension(150,20));
        changeBirthdayDate = new JButton("changeBirthdayDate");
        changeBirthdayDate.setPreferredSize(new Dimension(150,20));
        changeLocalDate = new JButton("changeLocalDate");
        changeLocalDate.setPreferredSize(new Dimension(150,20));
        changeX = new JButton("changeX");
        changeX.setPreferredSize(new Dimension(150,20));
        changeY = new JButton("changeY");
        changeY.setPreferredSize(new Dimension(150,20));
        changeAge = new JButton("changeAge");
        changeAge.setPreferredSize(new Dimension(150,20));
        remove = new JButton("remove");
        remove.setPreferredSize(new Dimension(150,20));
        create = new JButton("create");
        create.setPreferredSize(new Dimension(150,20));

        changePanel = new JPanel();

        nameField = new JTextField("nameField", 20);
        BirthdayDateField = new JTextField("BirthdayDateField", 20);
        localDateField = new JTextField("localDateField", 20);
        xField = new JTextField("xField",20);
        yField = new JTextField("yField", 20);
        ageField = new JTextField("ageField", 20);

        //MyTable.contents.add(remove);
        //MyTable.contents.add(create);

        JPanel cn = new JPanel();
        cn.add(nameField);
        cn.add(changeName);
        //MyTable.contents.add(cn);

        JPanel cb = new JPanel();
        cb.add(BirthdayDateField);
        cb.add(changeBirthdayDate);
        //MyTable.contents.add(cb);

        JPanel cl = new JPanel();
        cl.add(localDateField);
        cl.add(changeLocalDate);
        //MyTable.contents.add(cl);

        // TODO: Почему не отображаются эти панели?
        JPanel cx = new JPanel();
        cx.add(xField);
        cx.add(changeX);
        //MyTable.contents.add(cx);

        JPanel cy = new JPanel();
        cy.add(yField);
        cy.add(changeY);
        //MyTable.contents.add(cx);

        JPanel ca = new JPanel();
        ca.add(ageField);
        ca.add(changeAge);
        //MyTable.contents.add(cx);


    }
}
