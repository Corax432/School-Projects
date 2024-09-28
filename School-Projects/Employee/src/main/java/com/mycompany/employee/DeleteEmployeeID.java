package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteEmployeeID extends JDialog implements ActionListener{
    private JTextField id;;
    private JButton deleteButton;
    private JPanel panel;
    private JLabel label1;
    
    public DeleteEmployeeID(JFrame frame){
        super(frame, true);      
        setSize(180, 120);
        setLocationRelativeTo(frame);  
        createPanel();
    }
    private void createPanel(){
        Font font = new Font("Calibri", Font.BOLD, 25);
        Font labelFont = new Font("Calibri", Font.BOLD, 18);
        
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(label1 = new JLabel("Enter Employee ID"));
        label1.setFont(labelFont);
        topPanel.add(id = new JTextField());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton = new JButton("Delete"));
        deleteButton.setFont(font);
        deleteButton.setBackground(Color.DARK_GRAY);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);        

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        
        add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        dispose();
        //If the id is valid, deletes record
        if (MainMenu.checkID(id.getText())){
            try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu"); 
                PreparedStatement stat = conn.prepareStatement("DELETE FROM employees WHERE id = ?")){
                stat.setString(1, id.getText());
                stat.execute();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        //If the id is invalid or blank, resets window
        else{
            DeleteEmployeeID id = new DeleteEmployeeID(new JFrame());
            id.setVisible(true);
        }
    }
}
