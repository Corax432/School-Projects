package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseDatabase extends JDialog implements ActionListener {
    private JTextField urlField, userField, passwordField;
    private JButton connectButton;
    private String dbUrl, dbUser, dbPassword;

    public ChooseDatabase(JFrame parent){
        super(parent, "Choose Database", true);
      
        setLayout(new GridLayout(4, 2));
        setSize(400, 200);

        add(new JLabel("Database URL:"));
        urlField = new JTextField("jdbc:mysql://localhost:3306/employeedb");
        add(urlField);

        add(new JLabel("Username:"));
        userField = new JTextField("root");
        add(userField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        add(connectButton);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        dbUrl = urlField.getText();
        dbUser = userField.getText();
        dbPassword = passwordField.getText();
        
        setVisible(false);
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
