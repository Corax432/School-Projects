package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateEmployee extends JDialog implements ActionListener{
    //The "old" variables are the preexisting database values, prior to the update
    private String idOld, firstNameOld, surnameOld, startDateOld, startingSalaryOld, positionOld, ssnOld, 
            birthDateOld, phoneNumberOld, emergencyContactNameOld, emergencyContactPhoneOld;
    private Boolean contractSignedOld;
    private JTextField id, firstName, surname, startDate, startingSalary, position, ssn, birthDate, 
            phoneNumber, emergencyContactName, emergencyContactPhone;
    private JCheckBox contractSigned;
    private JButton saveButton;
    private JPanel panel;
    private String enteredID;
    private JLabel label1, label2, label3, label4, label5, label6, label7, 
            label8, label9, label10, label11, label12;

    public UpdateEmployee(JFrame frame, String ID){
        super(frame, true);
        setSize(370, 400);
        setLocationRelativeTo(frame);
        //enteredID is the id of the employee to be updated
        enteredID = ID;
        createPanel();
    }
    private void createPanel(){
        Font font = new Font("Calibri", Font.BOLD, 15);
        Font buttonFont = new Font("Calibri", Font.BOLD, 25);
        
        panel = new JPanel(new GridLayout(13, 1));
        //retrieveData retrieves the data associated with the entered id
        //This data is used to pre-fill the textfields
        retrieveData(enteredID);
        
        panel.add(label1 = new JLabel("                           Employee ID "));
        label1.setFont(font);
        panel.add(id = new JTextField(idOld));
        
        panel.add(label2 = new JLabel("                              First name "));
        label2.setFont(font);
        panel.add(firstName = new JTextField(firstNameOld));
        
        panel.add(label3 = new JLabel("                               Last name "));
        label3.setFont(font);
        panel.add(surname = new JTextField(surnameOld));
        
        panel.add(label4 = new JLabel("         Social security number "));
        label4.setFont(font);
        panel.add(ssn = new JTextField(ssnOld));
        
        panel.add(label5 = new JLabel("                           Date of birth "));
        label5.setFont(font);
        panel.add(birthDate = new JTextField(birthDateOld));
        
        panel.add(label6 = new JLabel("                       Phone number "));
        label6.setFont(font);
        panel.add(phoneNumber = new JTextField(phoneNumberOld));
        
        panel.add(label7 = new JLabel("                                Start date "));
        label7.setFont(font);
        panel.add(startDate = new JTextField(startDateOld));
        
        panel.add(label8 = new JLabel("                            Starting pay "));
        label8.setFont(font);
        panel.add(startingSalary = new JTextField(startingSalaryOld));
        
        panel.add(label9 = new JLabel("                                   Position "));
        label9.setFont(font);
        panel.add(position = new JTextField(positionOld));
        
        panel.add(label10 = new JLabel("    Emergency contact name"));
        label10.setFont(font);
        panel.add(emergencyContactName = new JTextField(emergencyContactNameOld));
        
        panel.add(label11 = new JLabel("   Emergency contact phone"));
        label11.setFont(font);
        panel.add(emergencyContactPhone = new JTextField(emergencyContactPhoneOld));
        
        panel.add(label12 = new JLabel("                    Contract signed?"));
        label12.setFont(font);
        panel.add(contractSigned = new JCheckBox("", contractSignedOld));
        
        panel.add(new JLabel());
        panel.add(saveButton = new JButton("Save Changes"));
        saveButton.setFont(buttonFont);
        saveButton.setBackground(Color.DARK_GRAY);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(this);     
        
        add(panel, BorderLayout.CENTER);
    }
    //Retrieves data to pre-fill the text fields, based on the entered id
    private void retrieveData(String ID){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu"); 
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM employees WHERE id = ?")){
            stat.setString(1, ID);
            ResultSet res = stat.executeQuery();
            if (res.next()){
                //The "old" values are set from the database values
                idOld = res.getString("id");
                firstNameOld = res.getString("firstName");
                surnameOld = res.getString("surname");
                ssnOld = res.getString("ssn");
                birthDateOld = res.getString("birthDate");
                phoneNumberOld = res.getString("phoneNumber");
                startDateOld = res.getString("startDate");
                startingSalaryOld = res.getString("startingSalary");
                positionOld = res.getString("position");
                emergencyContactNameOld = res.getString("emergencyContactName");
                emergencyContactPhoneOld = res.getString("emergencyContactPhone");
                contractSignedOld = res.getBoolean("contractSigned");
                
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    //Updates the data in the database after the user presses "save"
    @Override
    public void actionPerformed(ActionEvent e){
        dispose();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu");
            PreparedStatement stat = conn.prepareStatement("UPDATE employees SET firstName = ?, surname = ?, ssn = ?, birthDate = ?, phoneNumber = ?,"
            + "startDate = ?, startingSalary = ?, position = ?, emergencyContactName = ?, emergencyContactPhone = ?, contractSigned = ?, id = ? WHERE id = ?")){
            stat.setString(1, firstName.getText());
            stat.setString(2, surname.getText());
            stat.setString(3, ssn.getText());
            stat.setString(4, birthDate.getText());
            stat.setString(5, phoneNumber.getText());
            stat.setString(6, startDate.getText());
            stat.setString(7, startingSalary.getText());
            stat.setString(8, position.getText());
            stat.setString(9, emergencyContactName.getText());
            stat.setString(10, emergencyContactPhone.getText());
            stat.setBoolean(11, contractSigned.isSelected());
            stat.setString(12, id.getText());
            stat.setString(13, idOld);
            stat.execute();
        }
        catch (Exception ex){
        }
    }
}
