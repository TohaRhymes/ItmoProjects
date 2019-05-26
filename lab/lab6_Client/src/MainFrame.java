import localization.LocalizationManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private static LocalizationManager localizationManager;

    @Getter
    private Menu languageMenu;

    private static int count;

    private static JButton updateButton;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JLabel nameLabel;
    private static JLabel birthdayDatelabel;
    private static JLabel localDateLabel;
    private static JLabel xLabel;
    private static JLabel yLabel;
    private static JLabel ageLabel;
    //todo:toha текст и панель о некорректном вводе
    private static javax.swing.JLabel text;
    private javax.swing.JPanel textPanel;

    private JPanel tablePanel;
    private JPanel jPanelRightComp;
    private JPanel downButtonPanel;
    private JPanel upButtonPanel;
    public static GraphicsPanel downRightPanel;
    private ImagePanel iconPanel;
    private JScrollPane jScrollPane1;
    public static JSplitPane jSplitPane1;
    @Getter
    private static MyTable myTable;
    @Getter
    private static javax.swing.JTextField nameField;
    @Getter
    private static JFormattedTextField birthdayDateField;
    @Getter
    private static javax.swing.JTextField xField;
    @Getter
    private static javax.swing.JTextField localDateField;
    @Getter
    private static javax.swing.JTextField yField;
    @Getter
    private static javax.swing.JTextField ageField;
    private Point mouse;


    public MainFrame() {
        localizationManager = MainClient.localizationManager;
        languageMenu = new Menu();

        init();
        draw();

        this.setJMenuBar(languageMenu);
        this.setSize(1050, 700);
        this.setLocationRelativeTo(null); //центрирует окно
        this.setResizable(false); // Запрещает разворачивать окно
    }

    public void repaintMenuBar() {
        MainClient.getFrame().getLanguageMenu().repaint();
    }

    public void clearTextFields() {
        nameField.setText("");
        birthdayDateField.setText("");
        localDateField.setText("");
        xField.setText("");
        yField.setText("");
        ageField.setText("");

    }

    public void chooseCircle() {

        for (Circle circle : MyTable.getTardisModel().getCircles()) {
            if (circle.contains(mouse)) {
                int travNum = circle.getTravellerNumber();
                TimeTraveller trav = MyTable.getTardisModel().getData().get(travNum);
                setTraveller(trav, MainClient.getCookie().split("|")[0].equals(MyTable.getTardisModel().getData().get(circle.getTravellerNumber()).getOwner()));
                // TODO: Добавить в прект Антона
                int indexView = myTable.convertRowIndexToView(circle.getTravellerNumber());
                myTable.setRowSelectionInterval(indexView, indexView);
                break;
            }
        }
    }

    private void init() {
        count = 0;
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        jSplitPane1 = new javax.swing.JSplitPane();

        //todo: toha добавили панель с текстом об ошибке
        textPanel = new javax.swing.JPanel();
        textPanel.setBackground(new Color(136, 201, 237));
        text = new javax.swing.JLabel("");
        text.setForeground(Color.red);
        textPanel.add(text);

        tablePanel = new javax.swing.JPanel();
        // TODO: 1 новая строка
        tablePanel.setBackground(new Color(136, 201, 237));
        downButtonPanel = new JPanel();
        downButtonPanel.setBackground(new Color(136, 201, 237));
        addButton = new JButton();
        deleteButton = new javax.swing.JButton();
        jPanelRightComp = new javax.swing.JPanel();
        jPanelRightComp.setBackground(new Color(136, 201, 237));
        upButtonPanel = new javax.swing.JPanel();
        upButtonPanel.setBackground(new Color(136, 201, 237));
        nameLabel = new javax.swing.JLabel();
        birthdayDatelabel = new javax.swing.JLabel();
        localDateLabel = new javax.swing.JLabel();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        birthdayDateField = new JFormattedTextField(format);
        xField = new javax.swing.JTextField();
        localDateField = new javax.swing.JTextField();
        yField = new javax.swing.JTextField();
        ageField = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        iconPanel = new ImagePanel("frr.jpg");
        jScrollPane1 = new javax.swing.JScrollPane();
        mouse = new Point();

        downRightPanel = new GraphicsPanel();
        downRightPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                mouse.setLocation(me.getX(), me.getY());
                chooseCircle();
            }
        });

        myTable = new MyTable(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));

        jSplitPane1.setDividerLocation(530);
        jSplitPane1.setDividerSize(8);

        tablePanel.setPreferredSize(new java.awt.Dimension(500, 700));

        addButton.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_ADD));

        deleteButton.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_DELETE));
        jSplitPane1.setLeftComponent(tablePanel);

        jPanelRightComp.setPreferredSize(new java.awt.Dimension(540, 600));

        jScrollPane1.setViewportView(myTable);

        reloadLocale();

        jSplitPane1.setRightComponent(jPanelRightComp);

        // TODO: add method
        addButton.addActionListener(e -> {
            count++;
            if ((count % 2) != 0) {
                updateButton.setVisible(false);
                clearTextFields();
                nameField.setEditable(true);
                birthdayDateField.setEditable(false);
                localDateField.setEditable(false);
                xField.setEditable(true);
                yField.setEditable(true);
                ageField.setEditable(false);

            } else {
                String cookies = MainClient.getCookie().split("\\|")[0];
                TimeTraveller newTraveller = new TimeTraveller(nameField.getText(), new Double(xField.getText()), new Double(yField.getText()), MainClient.getCookie().split("\\|")[0]);
                ArrayList<String> args = new ArrayList();
                args.add(MainClient.getGson().toJson(newTraveller));
                nameField.setEditable(false);
                birthdayDateField.setEditable(false);
                localDateField.setEditable(true);
                xField.setEditable(true);
                yField.setEditable(true);
                ageField.setEditable(false);
                updateButton.setVisible(true);
                try {
                    MainClient.serverConnect.sendMSG(new ClientMSG("add", args));
                } catch (IOException e1) {
                    System.out.println("Добавление нового объекта");;
                }

            }


        });

        deleteButton.addActionListener(e -> {
            int rowIndex = myTable.getSelectedRow();
            if (myTable.getTardisModel().isCellEditable(myTable.getRowSorter().convertRowIndexToModel(rowIndex))) {

                //Отправляем сообщение об удалении на сервер
                ArrayList<String> args = new ArrayList<>();
                args.add(MainClient.getGson().toJson(myTable.getTardisModel().getData().get(myTable.getRowSorter().convertRowIndexToModel(rowIndex))));
                try {
                    MainClient.serverConnect.sendMSG(new ClientMSG("remove", args));
                } catch (IOException e1) {
                    System.out.println("Отправка сообщения об удалении");
                }
                // удаление строки из таблицы и ее перерисовка

            }

            // TODO: Если не могу работать с объектом
        });


        updateButton.addActionListener(e -> {


            //todo toha проверим корректность введенных данных
            Boolean correct = true;
            if (!isValidDate(localDateField.getText())) {
                correct = setFieldLightRed(localDateField);
            } else {
                localDateField.setBackground(Color.white);
            }

            if (!isDouble(xField.getText())) {
                correct = setFieldLightRed(xField);
            } else if (Double.valueOf(xField.getText()).compareTo(Double.valueOf(320)) > 0 || Double.valueOf(xField.getText()).compareTo(Double.valueOf(0)) < 0) {
                correct = setFieldLightRed(xField);
            } else {
                xField.setBackground(Color.white);
            }

            if (!isDouble(yField.getText())) {
                correct = setFieldLightRed(yField);
            } else if (Double.valueOf(0).compareTo(Double.valueOf(yField.getText())) > 0 || Double.valueOf(yField.getText()).compareTo(Double.valueOf(320)) > 0) {
                correct = setFieldLightRed(yField);
            } else {
                yField.setBackground(Color.white);
            }



            //todo:toha если корректно, то тогда обновляем поля
            if (correct) {
                MyTable.TardisModel model = myTable.getTardisModel();
                int rowIndex = myTable.getSelectedRow();
                TimeTraveller t = model.getTimeTravelerAtRow(rowIndex);
                t.setLocDate(new OurDate(localDateField.getText()));
                t.setX(Double.parseDouble(xField.getText()));
                t.setY(Double.parseDouble(yField.getText()));
                model.fireTableDataChanged();
                //[rowIndex]

                //MainClient.datamass.set(rowIndex, MainClient.getGson().toJson(t));
                //ArrayList<String> args = new ArrayList<>();
                //args.add(MainClient.datamass.get(rowIndex));
                ArrayList<String> args = new ArrayList<>();
                args.add(MainClient.getGson().toJson(t));

                ClientMSG clientMSG = new ClientMSG("update", args);
                try {
                    MainClient.serverConnect.sendMSG(clientMSG);

                    nameField.setText("");
                    birthdayDateField.setText("");
                    xField.setText("");
                    localDateField.setText("");
                    yField.setText("");
                    ageField.setText("");

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }

    private void draw() {
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(downButtonPanel);
        downButtonPanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(addButton).addComponent(deleteButton)).addContainerGap(166, Short.MAX_VALUE)));


        GroupLayout tablePanelLayout = new GroupLayout(tablePanel); // Создаем Layout
        tablePanel.setLayout(tablePanelLayout); // Устанавливаем Layout
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablePanelLayout.createSequentialGroup()
                        .addContainerGap().addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(downButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap().addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));


        GroupLayout iconPanelLayout = new GroupLayout(iconPanel); // Создаем Layout
        iconPanel.setLayout(iconPanelLayout); // Устанавливаем Layout
        iconPanel.setBackground(Color.CYAN);
        // TODO: Добавление картинки в панель

        iconPanelLayout.setHorizontalGroup(iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        iconPanelLayout.setVerticalGroup(iconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(upButtonPanel);
        upButtonPanel.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(jPanel4Layout.createSequentialGroup().addComponent(nameLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(birthdayDatelabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(birthdayDateField)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(ageLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(ageField)).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(localDateLabel).addComponent(xLabel).addComponent(yLabel)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(localDateField).addComponent(xField).addComponent(yField)))).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE).addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(53, 53, 53)).addGroup(jPanel4Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(iconPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()))));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(nameLabel).addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(birthdayDatelabel).addComponent(birthdayDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(localDateLabel).addComponent(localDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(xLabel).addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(yLabel).addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addComponent(iconPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(ageLabel).addComponent(updateButton)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(downRightPanel);
        downRightPanel.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 398, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanelRightComp);
        jPanelRightComp.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(downRightPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(upButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap(36, Short.MAX_VALUE).addComponent(upButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(downRightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1));


        javax.swing.GroupLayout layout1 = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE));
        layout1.setVerticalGroup(layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1));

        pack();
    }

    public void setTraveller(TimeTraveller timeT, boolean enable) {

        // TODO: Можно менять только на уникальное или не менять вообще
        nameField.setEditable(false);
        // TODO: Дату рождения не создавать в реальном времени, а указывать при создании
        birthdayDateField.setEditable(false);
        localDateField.setEditable(enable);
        xField.setEditable(enable);
        yField.setEditable(enable);
        ageField.setEditable(false);

        nameField.setText(timeT.getName());
        birthdayDateField.setText(timeT.getBirthdayDate().DatetoString());
        xField.setText(timeT.getX() + "");
        localDateField.setText(timeT.getLocDate().DatetoString());
        yField.setText(timeT.getY() + "");
        ageField.setText(timeT.getAge() + "");
    }

    // метод обновляет все надписи (кроме таблицы) в соответствии с действующей локалью (т.е. если поменяли локаль надо вызвать его, чтобы обновить надписи)
    public static void reloadLocale() {

        nameLabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_NAME));
        birthdayDatelabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_DATEOFBIRTHDAY));
        localDateLabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_CURDATE));
        xLabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_X));
        yLabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_Y));
        ageLabel.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_AGE));

        nameField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_NAME));
        xField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_X));
        yField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_Y));
        birthdayDateField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_DATEOFBIRTHDAY));
        localDateField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_CURDATE));
        ageField.setText(localizationManager.getNativeTitle(LocalizationManager.TITLE_AGE));

        addButton.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_ADD));
        deleteButton.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_DELETE));
        updateButton.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_UPDATE));

        localDateField.setToolTipText(localizationManager.getNativeTitle(LocalizationManager.TITLE_DATEFORMAT));
        xField.setToolTipText(localizationManager.getNativeTitle(LocalizationManager.TITLE_NUMBERFORMAT));
        yField.setToolTipText(localizationManager.getNativeTitle(LocalizationManager.TITLE_NUMBERFORMAT));

    }

    // метод обновляет надписи на таблице в соответствии с действующей локалью (т.е. если поменяли локаль надо вызвать его, чтобы обновить надписи)
    public static void reloadColumnNames() {
        for (int i = 0; i < 6; i++)
            myTable.getColumnModel().getColumn(i).setHeaderValue(myTable.getColumnName(i));
        myTable.getTableHeader().resizeAndRepaint();
    }


    //todo: toha метод проверяет является ли строка датой
    public boolean isValidDate(String dateString) {
        try {
            String s = dateString.concat("T10:15:30+01:00");
            OffsetDateTime date = OffsetDateTime.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //todo: toha метод проверяет является ли строка double
    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //todo: toha сделать поле красным и вернуть ложь (чтобы пометить, что поле некорректно)
    public static boolean setFieldLightRed(javax.swing.JTextField field) {
        Color lightRed = new Color(0xFF4545);
        field.setBackground(lightRed);
        return false;
    }
}
