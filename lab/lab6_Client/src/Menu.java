import localization.LocalizationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JMenuBar {

    private static JMenu hiMenu;
    private static JMenu filters;
    private static JMenuItem setFilter;
    private static JMenuItem useFilter;
    private static JMenuItem clearFilter;

    private static JMenuItem logOut;

    static LocalizationManager localizationManager;
    // TODO: Изменить шрифт

    public Menu() {

        localizationManager = MainClient.localizationManager;

        // TODO: Новый пункт меню, добавить локализацию

        hiMenu = new JMenu(localizationManager.getNativeButton(LocalizationManager.BUTTON_HI)+", " + MainClient.getCookie().split("\\|")[0]+"!");
        this.add(hiMenu);

        logOut = new JMenuItem(localizationManager.getNativeButton(LocalizationManager.BUTTON_LOGOUT));

        hiMenu.add(logOut);
        logOut.addActionListener(e -> {
            MainClient.setCookie("unlogin|cat");
            MainClient.getFrame().setVisible(false);
            MainClient.getLoginOrRegisterWindow().setVisible(true);
        });


        JMenu languageMenu = new JMenu("Language");
        Font font = new Font("Verdana", Font.PLAIN, 11);

        JMenuItem russian = new JMenuItem("Русский язык");
        russian.setFont(font);
        languageMenu.add(russian);

        JMenuItem estonian = new JMenuItem("Eesti keel");
        estonian.setFont(font);
        languageMenu.add(estonian);

        JMenuItem greek = new JMenuItem("Ελληνικά");
        greek.setFont(font);
        languageMenu.add(greek);

        JMenuItem spanish = new JMenuItem("Español");
        spanish.setFont(font);
        languageMenu.add(spanish);
        this.add(languageMenu);

        greek.addActionListener(e -> {
            localizationManager.setLocale(LocalizationManager.COUNTRY_GR);
            MainFrame.reloadLocale();
            MainFrame.reloadColumnNames();
        });
        russian.addActionListener(e -> {
            localizationManager.setLocale(LocalizationManager.COUNTRY_RU);
            MainFrame.reloadLocale();
            MainFrame.reloadColumnNames();
        });
        spanish.addActionListener(e -> {
            localizationManager.setLocale(LocalizationManager.COUNTRY_HN);
            MainFrame.reloadLocale();
            MainFrame.reloadColumnNames();
        });
        estonian.addActionListener(e -> {
            localizationManager.setLocale(LocalizationManager.COUNTRY_EE);
            MainFrame.reloadLocale();
            MainFrame.reloadColumnNames();
        });

        // TODO: Новый пункт меню, добавить локализацию
        filters = new JMenu(localizationManager.getNativeButton(LocalizationManager.BUTTON_FILTER));

        setFilter = new JMenuItem(localizationManager.getNativeButton(LocalizationManager.BUTTON_SETFILTER));
        useFilter = new JMenuItem(localizationManager.getNativeButton(LocalizationManager.BUTTON_USEFILTER));
        clearFilter = new JMenuItem(localizationManager.getNativeButton(LocalizationManager.BUTTON_CLEARFILTER));

        filters.add(setFilter);
        filters.add(useFilter);
        filters.add(clearFilter);

        this.add(filters);

        setFilter.addActionListener(e -> {
            MainClient.getFrame().clearTextFields();

            MainClient.getFrame().getNameField().setEditable(true);
            MainClient.getFrame().getXField().setEditable(true);
            MainClient.getFrame().getYField().setEditable(true);
            MainClient.getFrame().getAgeField().setEditable(true);

            MainClient.getFrame().getBirthdayDateField().setEditable(false);
            MainClient.getFrame().getLocalDateField().setEditable(false);
        });

        clearFilter.addActionListener(e -> {
            MainClient.getFrame().clearTextFields();
            MainClient.getFrame().getMyTable().sorter.setRowFilter(null);
            MainClient.getFrame().getMyTable().setRowSelectionInterval(0, 0);
        });

        useFilter.addActionListener(e -> {
            // Отсюда пойдут фильтры

            List<RowFilter<Object, Object>> filterList = new ArrayList<RowFilter< Object, Object>>(2);

            // Фильтр по имени
            if (!MainClient.getFrame().getNameField().getText().equals("")) {
                RowFilter nameFilter = null;
                nameFilter = RowFilter.regexFilter(MainClient.getFrame().getNameField().getText(), 0);
                filterList.add(nameFilter);
            }

            // Фильтр по x координате
            if (!MainClient.getFrame().getXField().getText().equals("")) {
                RowFilter xFilter = null;
                xFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, new Integer(MainClient.getFrame().getXField().getText()), 3);
                //sorter.setRowFilter(xFilter);
                filterList.add(xFilter);
            }

            // Фильтр по y координате
            if (!MainClient.getFrame().getYField().getText().equals("")) {
                RowFilter yFilter = null;
                yFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, new Integer(MainClient.getFrame().getYField().getText()), 4);
                //sorter.setRowFilter(yFilter);
                filterList.add(yFilter);
            }

            // Фильтр по возрасту
            if (!MainClient.getFrame().getAgeField().getText().equals("")) {
                RowFilter ageFilter = null;
                ageFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, new Integer(MainClient.getFrame().getAgeField().getText()), 5);
                //sorter.setRowFilter(ageFilter);
                filterList.add(ageFilter);
            }

            RowFilter<Object, Object> megaFilter = RowFilter.andFilter(filterList);
            MainClient.getFrame().getMyTable().sorter.setRowFilter(megaFilter);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        hiMenu.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_HI)+", " + MainClient.getCookie().split("\\|")[0]+"!");
        logOut.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_LOGOUT));
        filters.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_FILTER));
        setFilter.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_SETFILTER));
        useFilter.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_USEFILTER));
        clearFilter.setText(localizationManager.getNativeButton(LocalizationManager.BUTTON_CLEARFILTER));
    }
}
