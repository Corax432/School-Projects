package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainMenu extends JFrame implements ActionListener{
    private JButton button1, button2;
    private JLabel titleLabel;
    private String dbUrl, dbUser, dbPassword;

    public MainMenu(){
        setSize(1285, 680);
        chooseDatabase();
        createEmployeeTable();
        createPanels();
    }
    
    private void chooseDatabase(){
        ChooseDatabase chooseDbDialog = new ChooseDatabase(this);
        dbUrl = chooseDbDialog.getDbUrl();
        dbUser = chooseDbDialog.getDbUser();
        dbPassword = chooseDbDialog.getDbPassword();
    }
    //Connects to databse and creates table
    private void createEmployeeTable(){
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword); 
            Statement stat = conn.createStatement()){
            stat.execute("CREATE TABLE IF NOT EXISTS employees (ID VARCHAR(20) PRIMARY KEY, firstName VARCHAR(20), surname VARCHAR(20), ssn VARCHAR(20),"
                + "birthDate VARCHAR(20), phoneNumber VARCHAR(20), startDate VARCHAR(20), startingSalary VARCHAR(20),"
                + "position VARCHAR (20), emergencyContactName VARCHAR(20), emergencyContactPhone VARCHAR(20), contractSigned BOOLEAN)");              
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    //Creates upper and lower panels
    private void createPanels(){
        Font titleFont = new Font("Georgia", Font.BOLD, 45);
        Font font = new Font("Calibri", Font.BOLD, 70);
        Color fontColor = Color.WHITE;
        Color buttonColor = Color.DARK_GRAY;
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.GRAY);
        titlePanel.add(titleLabel = new JLabel("ABC Company Employee Management System"));
        titleLabel.setFont(titleFont);   
        titleLabel.setForeground(Color.BLACK);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBackground(Color.BLACK);
        
        button1 = new JButton("New Employee");
        button1.setFont(font);
        button1.setBackground(buttonColor);
        button1.setForeground(fontColor);
        button1.addActionListener(this);
      
        button2 = new JButton("View Employees");
        button2.setFont(font);
        button2.setBackground(buttonColor);
        button2.setForeground(fontColor);
        button2.addActionListener(this);
        
        buttonPanel.add(button1);
        buttonPanel.add(button2);
      
        add(titlePanel, "North");
        add(buttonPanel, "Center");
    }
    //Checks if the employee id is in the database
    public boolean checkID(String ID){
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)){ 
            String string = "SELECT * FROM employees WHERE id = ?";
            ResultSet res;
            try (PreparedStatement stat = conn.prepareStatement(string)){
                stat.setString(1, ID);
                res = stat.executeQuery();
                //If data is found in the resultset, returns true
                if(res.next()){
                    return true;
                }
            }
            res.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    //Checks for button presses and opens the correct window
    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        //Button 1 is for creating an employee
        if (source == button1){
            CreateEmployee create = new CreateEmployee(this);
            create.setVisible(true);
        }
        //Button 2 is for viewing and updating employee data
        else if (source == button2){
            EmployeeList list = new EmployeeList(this);
            list.setVisible(true);
        }
    }  
    public static void main(String[] args){
        JFrame mainMenu = new MainMenu();
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setVisible(true);
    }
}
