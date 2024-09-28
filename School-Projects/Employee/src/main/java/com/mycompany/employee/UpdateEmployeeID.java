package com.mycompany.employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateEmployeeID extends JDialog implements ActionListener{
    private JTextField id;;
    private JButton enterButton;
    private JPanel panel;
    private JLabel label1;
    
    public UpdateEmployeeID(JFrame frame){
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
        bottomPanel.add(enterButton = new JButton("Enter"));
        enterButton.setFont(font);
        enterButton.setBackground(Color.DARK_GRAY);
        enterButton.setForeground(Color.WHITE);
        enterButton.addActionListener(this);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        
        add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        dispose();
        //If the id is valid, moves on to the update window
        if (MainMenu.checkID(id.getText())){
            UpdateEmployee update = new UpdateEmployee(new JFrame(), id.getText());
            update.setVisible(true);
        }
        //If the id is invalid or blank, resets window
        else{
            UpdateEmployeeID id = new UpdateEmployeeID(new JFrame());
            id.setVisible(true);
        }
    }
}
