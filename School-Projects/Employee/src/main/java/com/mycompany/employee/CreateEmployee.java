package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CreateEmployee extends JDialog implements ActionListener{
    private JTextField id, firstName, surname, ssn, birthDate, phoneNumber, 
            startDate, startingSalary, position, emergencyContactName, emergencyContactPhone;
    private JCheckBox contractSigned;
    private JButton saveButton;
    private JPanel panel;
    private JLabel label1, label2, label3, label4, label5, label6, label7, 
            label8, label9, label10, label11, label12;
    
    public CreateEmployee(JFrame frame){
        super(frame, true); 
        setSize(350, 400);
        setLocationRelativeTo(frame);     
        createPanel();
    }
    private void createPanel(){
        Font font = new Font("Calibri", Font.BOLD, 15);
        Font buttonFont = new Font("Calibri", Font.BOLD, 25);
        
        panel = new JPanel(new GridLayout(13, 1));
        //Adds labels and text fields
        panel.add(label1 = new JLabel("                        Employee ID "));
        label1.setFont(font);
        panel.add(id = new JTextField());
        
        panel.add(label2 = new JLabel("                           First name "));
        label2.setFont(font);
        panel.add(firstName = new JTextField());
        
        panel.add(label3 = new JLabel("                            Last name "));
        label3.setFont(font);
        panel.add(surname = new JTextField());
        
        panel.add(label4 = new JLabel("      Social security number "));
        label4.setFont(font);
        panel.add(ssn = new JTextField());
        
        panel.add(label5 = new JLabel("                        Date of birth "));
        label5.setFont(font);
        panel.add(birthDate = new JTextField());
        
        panel.add(label6 = new JLabel("                    Phone number "));
        label6.setFont(font);
        panel.add(phoneNumber = new JTextField());
        
        panel.add(label7 = new JLabel("                             Start date "));
        label7.setFont(font);
        panel.add(startDate = new JTextField());
        
        panel.add(label8 = new JLabel("                         Starting pay "));
        label8.setFont(font);
        panel.add(startingSalary = new JTextField());
        
        panel.add(label9 = new JLabel("                                Position "));
        label9.setFont(font);
        panel.add(position = new JTextField());
        
        panel.add(label10 = new JLabel("  Emergency contact name "));
        label10.setFont(font);
        panel.add(emergencyContactName = new JTextField());
        
        panel.add(label11 = new JLabel(" Emergency contact phone "));
        label11.setFont(font);
        panel.add(emergencyContactPhone = new JTextField());
        
        panel.add(label12 = new JLabel("                 Contract signed?"));
        label12.setFont(font);
        panel.add(contractSigned = new JCheckBox());

        panel.add(new JLabel());
        panel.add(saveButton = new JButton("Save"));
        saveButton.setBackground(Color.DARK_GRAY);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(this);
        
        add(panel, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        dispose();
        //If id is left blank or contains a space, resets window
        if (id.getText().contains(" ") || id.getText().equals("")){
            CreateEmployee create = new CreateEmployee(new JFrame ());
            create.setVisible(true);
        }
        //If id is already in the database, resets the window
        else if (MainMenu.checkID(id.getText()) == true){
            CreateEmployee create = new CreateEmployee(new JFrame ());
            create.setVisible(true);
        }
        else{
            //Inserts the data from the text fields into the database
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu");
                PreparedStatement stat = conn.prepareStatement("INSERT INTO employees (id, firstName, surname, ssn, birthDate, phoneNumber,"
                + "startDate, startingSalary, position, emergencyContactName, emergencyContactPhone,  contractSigned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){    
                    stat.setString(1, id.getText());
                    stat.setString(2, firstName.getText());
                    stat.setString(3, surname.getText());
                    stat.setString(4, ssn.getText());
                    stat.setString(5, birthDate.getText());
                    stat.setString(6, phoneNumber.getText());
                    stat.setString(7, startDate.getText());
                    stat.setString(8, startingSalary.getText());
                    stat.setString(9, position.getText());
                    stat.setString(10, emergencyContactName.getText());
                    stat.setString(11, emergencyContactPhone.getText());
                    stat.setBoolean(12, contractSigned.isSelected());
                    stat.execute();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
