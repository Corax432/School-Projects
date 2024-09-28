package com.mycompany.employee;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.table.*;

public class EmployeeList extends JDialog implements ActionListener{
    private JTable employeeTable;
    private JButton createButton, editButton, deleteButtonid, deleteButton;
    private JScrollPane scrollPane;
    private DefaultTableModel model;   
    
    public EmployeeList(JFrame frame)
    {
        super(frame, true);
        setSize(1285, 350);
        setLocationRelativeTo(frame);   
        buttonPanel();
        createTable();          
    }
    private void buttonPanel()
    {
        Font font = new Font("Calibri", Font.BOLD, 45);
        Color fontColor = Color.WHITE;
        Color buttonColor = Color.DARK_GRAY;
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setBackground(Color.BLACK);
        
        panel.add(createButton = new JButton("Create"));
        createButton.setFont(font);
        createButton.setBackground(buttonColor);
        createButton.setForeground(fontColor);
        createButton.addActionListener(this);
        
        panel.add(editButton = new JButton("Update"));
        editButton.setFont(font);
        editButton.setBackground(buttonColor);
        editButton.setForeground(fontColor);
        editButton.addActionListener(this);
        
        panel.add(deleteButtonid = new JButton("Delete"));
        deleteButtonid.setFont(font);
        deleteButtonid.setBackground(buttonColor);
        deleteButtonid.setForeground(fontColor);
        deleteButtonid.addActionListener(this);
        
        panel.add(deleteButton = new JButton("Delete All"));
        deleteButton.setFont(font);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(fontColor);
        deleteButton.addActionListener(this);

        add(panel, "South");
    }
    private void createTable(){    
        //Creates new model for JTable
        model = new DefaultTableModel();
        model.addColumn("Employee ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("SSN");
        model.addColumn("Birth Date");
        model.addColumn("Phone Number");
        model.addColumn("Start Date");
        model.addColumn("Starting Pay");
        model.addColumn("Position");
        model.addColumn("Contract Signed?");
        model.addColumn("Emergency Contact Name");
        model.addColumn("Emergency Contact Phone");    
        //Adds data to model
        getData();
        employeeTable = new JTable(model);
        //Sets width of columns and centers data in them   
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(90);
        employeeTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(8).setPreferredWidth(95);
        employeeTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(9).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(10).setPreferredWidth(160);
        employeeTable.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        
        employeeTable.getColumnModel().getColumn(11).setPreferredWidth(160);
        employeeTable.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        //Makes table scrollable
        scrollPane = new JScrollPane(employeeTable);    
        add(scrollPane);
    }
    //Takes data from the database and adds it to the model
    private void getData(){
        //Resetting rowcount to 0 with each call of this method  
        //ensures that the displayed table data updates without having to reopen the window
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu"); 
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM employees"); ResultSet res = stat.executeQuery()){
            //While there are more rows to check, add the data from the next row to the array
            while (res.next()){
                //currentRow[0] - currentRow[10] represent the columns of the current row being checked
                Object[] currentRow = new Object[12];
                //Spacing is an attempt to center the data in the columns
                currentRow[0] = res.getString("ID");
                currentRow[1] = res.getString("firstName");
                currentRow[2] = res.getString("surname");
                currentRow[3] = res.getString("ssn");
                currentRow[4] = res.getString("birthDate");
                currentRow[5] = res.getString("phoneNumber");
                currentRow[6] = res.getString("startDate");
                currentRow[7] = res.getString("startingSalary");
                currentRow[8] = res.getString("position");
                Boolean bool = res.getBoolean("contractSigned");
                //Presents table data about the contract as "Yes/no" rather than "True/false"
                if (bool){
                    currentRow[9] = "Yes";
                }
                else{
                    currentRow[9] = "No";
                }
                currentRow[10] = res.getString("emergencyContactName");
                currentRow[11] = res.getString("emergencyContactPhone");              
                //Adds the Object array as a row to the model
                model.addRow(currentRow);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        //If the user presses create, opens a window to create employee
        if (source == createButton){
            CreateEmployee create = new CreateEmployee(new JFrame());
            create.setVisible(true);
            //getData() updates the data in the table after the createEmployee window closes
            getData();
        }
        //If the user presses update, opens window to enter employee id
        else if (source == editButton){
            UpdateEmployeeID id = new UpdateEmployeeID(new JFrame());
            id.setVisible(true);
            getData();
        }
        //If the user presses delete, opens window to enter employee id
        else if (source == deleteButtonid){   
            DeleteEmployeeID delete = new DeleteEmployeeID(new JFrame());
            delete.setVisible(true);
            getData();
        }
        //If the user presses delete all, opens confirmation window
        else{
            DeleteConfirmation confirm = new DeleteConfirmation(new JFrame());
            confirm.setVisible(true);
            getData();
        }
    }
}

