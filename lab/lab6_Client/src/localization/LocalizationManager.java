package localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    //константы для локалей
    public static final String LANGUAGE_RU = "ru";
    public static final String COUNTRY_RU = "RU";
    public static final String LANGUAGE_EL = "el";
    public static final String COUNTRY_GR = "GR";
    public static final String LANGUAGE_ES = "es";
    public static final String COUNTRY_HN = "HN";
    public static final String LANGUAGE_ET = "et";
    public static final String COUNTRY_EE = "EE";

    // константы имен кнопок и названий
    public static final String BUTTON_ADD = "add";
    public static final String BUTTON_DELETE = "delete";
    public static final String BUTTON_LOGIN = "login";
    public static final String BUTTON_REGISTER = "register";
    public static final String BUTTON_UPDATE = "update";
    public static final String BUTTON_FILTER = "filter";
    public static final String BUTTON_SETFILTER = "setFilter";
    public static final String BUTTON_USEFILTER = "useFilter";
    public static final String BUTTON_CLEARFILTER = "clearFilter";
    public static final String BUTTON_LOGOUT = "logOut";
    public static final String BUTTON_HI = "hi";
    public static final String TITLE_NAME = "name";
    public static final String TITLE_DATEOFBIRTHDAY = "dateOfBirth";
    public static final String TITLE_CURDATE = "curDate";
    public static final String TITLE_AGE = "age";
    public static final String TITLE_LANGUAGE = "language";
    public static final String TITLE_LOGINORREGISTER = "loginOrRegister";
    public static final String TITLE_X = "x";
    public static final String TITLE_Y = "y";
    public static final String TITLE_DATEFORMAT = "dateFormat";
    public static final String TITLE_NUMBERFORMAT = "numberFormat";




    private static Locale locale;
    public static ResourceBundle buttons;
    public static ResourceBundle titles;


    public LocalizationManager(){
        setLocale(COUNTRY_RU);
    }
    public LocalizationManager(String s){
        setLocale(s);
    }

    public void setLocale(String s){
        switch(s) {
            case "EE":
                locale = new Locale(LANGUAGE_ET, COUNTRY_EE);
                break;
            case "GR":
                locale = new Locale(LANGUAGE_EL, COUNTRY_GR);
                break;
            case "HN":
                locale = new Locale(LANGUAGE_ES, COUNTRY_HN);
                break;
            default:
                locale = new Locale(LANGUAGE_RU, COUNTRY_RU);
                break;
        }
        changeLanguage();

    }

    private void changeLanguage(){
        buttons = ResourceBundle.getBundle("localization.asciiFiles.buttons", locale);
        titles = ResourceBundle.getBundle("localization.asciiFiles.titles", locale);
    }
    public String getNativeButton(String button){
        return buttons.getString(button);
    }
    public String getNativeTitle(String title){
        return titles.getString(title);
    }



}