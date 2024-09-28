package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteConfirmation extends JDialog implements ActionListener{
    private JButton cancelButton, confirmButton;
    private JPanel panel;
    private JLabel label1, label2;
    
    public DeleteConfirmation(JFrame frame){
        super(frame, true);    
        setSize(445, 130);
        setLocationRelativeTo(frame);       
        createPanel();
    }
    private void createPanel(){
        Font font = new Font("Calibri", Font.BOLD, 25);
        Font labelFont = new Font("Calibri", Font.BOLD, 18);
         
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(label1 = new JLabel(" Are you sure you want to delete all employee records?"));
        label1.setFont(labelFont);
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(label2 = new JLabel("                                This cannot be undone."));
        label2.setFont(labelFont);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(cancelButton = new JButton("Cancel"));
        cancelButton.setFont(font);
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
            
        bottomPanel.add(confirmButton = new JButton("Confirm"));
        confirmButton.setFont(font);
        confirmButton.setBackground(Color.DARK_GRAY);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(this);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        //If anything is pressed, the window will close
        dispose();
        Object source = e.getSource();
        //If confirm button is pressed, window closes but deletion proceeds
        if (source == confirmButton){
            try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "Compsci221@psu"); 
                PreparedStatement stat = conn.prepareStatement("DELETE FROM employees")){
                stat.execute();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
