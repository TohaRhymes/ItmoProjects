import localization.LocalizationManager;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LoginOrRegisterWindow {

    private static LocalizationManager localizationManager;

    @Getter
    private JFrame frame;
    private JButton registerButton;
    private JButton loginButton;
    public static JTextField loginField;
    public static JTextField emailField;
    public static JPasswordField passwordField;
    private static JLabel text;

    private static JLabel registerText;
    @Getter
    private static JPanel inputPanel;
    private static JPanel buttonsPanel;
    private static JPanel textPanel;
    private static JPanel textRegPanel;
    private static String newLogin;
    //private static ImagePanel imagePanel;


    public LoginOrRegisterWindow(boolean login) {

        localizationManager = MainClient.localizationManager;

        // TODO: Размещение компонентов (frame.setXXXLayout(new XXXLayout());)

        // BorderLayout: frame.add(компонент; константа,определяющая местоположение[BorderLayout.XXX])
        // Делит область на 5 частей: верх, низ, центр, лево и право

        // FlowLayout: frame.add(компонент; константа,определяющая местоположение[BorderLayout.XXX])
        // Делит область на 5 частей: верх, низ, центр, лево и право

        // Панель с кнопками
        buttonsPanel = new JPanel(); // Позволяет хранить в себе другие панели, формы и компоненты (все, что наследуется от Component), т.е. является своеобразным контейнером
        // Панель с полями ввода
        inputPanel = new JPanel();

        // Панель с текстом об ошибке
        textPanel = new JPanel();
        //imagePanel = new ImagePanel("кот.jpg");
        //imagePanel.setVisible(true);

        buttonsPanel.setBackground(new Color(136, 201, 237)); // Покрасим панель
        inputPanel.setBackground(new Color(136, 201, 237));
        textPanel.setBackground(new Color(136, 201, 237));

        frame = new JFrame(localizationManager.getNativeTitle(LocalizationManager.TITLE_LOGINORREGISTER));
        frame.setResizable(false); // Запрещает растягивать окно
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        text = new JLabel("");
        text.setForeground(Color.red); // TODO: Поменять цвет текста сообщений об ошибке, он мерзкий


        frame.setLocationRelativeTo(null); //центрирует окно
        frame.setLayout(new BorderLayout());


        loginField = new JTextField(10);
        loginField.setBorder(new TitledBorder("login"));
        loginField.setText("B"); // TODO: Потом брать

        if (login) {
            passwordField = new JPasswordField(10);
            passwordField.setBorder(new TitledBorder("password"));
            passwordField.setText("cotik"); // TODO: Потом убрать

            loginButton = new JButton(localizationManager.getNativeButton(LocalizationManager.BUTTON_LOGIN));
            loginButton.addActionListener(e -> {
                ArrayList<String> args = new ArrayList<>();
                args.add(loginField.getText());
                args.add(new String(passwordField.getPassword()));

                try {
                    MainClient.serverConnect.sendMSG(new ClientMSG("login", args));
                } catch (IOException e1) {
                    System.out.println("Ошибка регистрации");
                }
            });

            registerButton = new JButton(localizationManager.getNativeButton(LocalizationManager.BUTTON_REGISTER));

            registerButton.addActionListener(e -> {
                frame.setVisible(false);
                frame = new LoginOrRegisterWindow(false).getFrame();
            });

            buttonsPanel.add(registerButton);
            buttonsPanel.add(loginButton);
            frame.add(buttonsPanel, BorderLayout.SOUTH); // добавляем панель в форму

            textPanel.add(text);
            frame.add(textPanel);

            inputPanel.add(loginField);
            inputPanel.add(passwordField);

            frame.add(inputPanel, BorderLayout.NORTH);
            //frame.add(imagePanel, BorderLayout.CENTER);

        } else {
            registerText = new javax.swing.JLabel("");
            registerText.setForeground(Color.red);

            textRegPanel = new JPanel();
            textRegPanel.setBackground(new Color(136, 201, 237));


            frame.setTitle(localizationManager.getNativeButton(LocalizationManager.BUTTON_REGISTER));
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            emailField = new JTextField(10);
            emailField.setBorder(new TitledBorder("email"));

            loginButton = new JButton(localizationManager.getNativeButton(LocalizationManager.BUTTON_REGISTER));
            loginButton.addActionListener(e -> {
                newLogin = loginField.getText();
                String email = emailField.getText();
                if (newLogin.length() == 0 && email.length() == 0) {
                    showMes("Введите логин и e-mail.");
                } else if (newLogin.length() == 0) {
                    showMes("Введите логин.");
                } else if (email.length() == 0) {
                    showMes("Введите e-mail.");
                } else {
                    ArrayList<String> args = new ArrayList<>();
                    args.add(newLogin);
                    args.add(email);
                    try {
                        MainClient.serverConnect.sendMSG(new ClientMSG("register", args));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            buttonsPanel.add(loginButton);
            frame.add(buttonsPanel, BorderLayout.SOUTH); // добавляем панель в форму

            textRegPanel.add(registerText);
            frame.add(textRegPanel, BorderLayout.CENTER);

            inputPanel.add(loginField);
            inputPanel.add(emailField);
            inputPanel.add(text);
            frame.add(inputPanel, BorderLayout.NORTH);
        }
        frame.setVisible(true);
    }

    public void showMes(String s) {
        Color lightRed = new Color(0xFF4545);
        if (s.trim().equals("Некорректный логин")) {
            loginField.setBackground(lightRed);
            passwordField.setBackground(Color.white);
            text.setText(s);
        } else if (s.trim().equals("Некорректный пароль")) {
            loginField.setBackground(Color.white);
            passwordField.setBackground(lightRed);
            text.setText(s);
        } else if (s.trim().equals("Введите логин.")) {
            loginField.setBackground(lightRed);
            emailField.setBackground(Color.white);
            registerText.setForeground(Color.red);
            registerText.setText(s);
        } else if (s.trim().equals("Введите e-mail.")) {
            loginField.setBackground(Color.white);
            emailField.setBackground(lightRed);
            registerText.setForeground(Color.red);
            registerText.setText(s);
        } else if (s.trim().equals("Введите логин и e-mail.")) {
            loginField.setBackground(lightRed);
            emailField.setBackground(lightRed);
            registerText.setForeground(Color.red);
            registerText.setText(s);
        } else {
            loginField.setBackground(lightRed);
            passwordField.setBackground(lightRed);
            text.setText("Ошибка входа. Повторите попытку.");
        }
    }

    public void successfulReg() {
        frame.setVisible(false);
        frame = new LoginOrRegisterWindow(true).getFrame();
        loginField.setBackground(Color.white);
        passwordField.setBackground(Color.white);
        text.setForeground(Color.BLACK);
        loginField.setText(newLogin);
        passwordField.setText("");
        text.setText("Регистрация прошла успешно, пароль выслан вам на почту");
    }

    public void incorrectReg() {
        Color lightRed = new Color(0xFF4545);
        loginField.setBackground(lightRed);
        text.setText("Такой логин уже существует");
        emailField.setBackground(Color.white);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}

