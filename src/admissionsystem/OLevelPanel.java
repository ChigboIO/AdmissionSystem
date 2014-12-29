/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SCIENCE
 */
public class OLevelPanel extends JPanel {
    JPanel required_panel, optional_panel, south_panel, required_grid_panel, optional_grid_panel;
    JComboBox[] required;
    JComboBox[] optional;
    JTextField optionals;
    JButton save, close;
    Statement statement;
    ResultSet result;
    String dept;
    public OLevelPanel(String dept){
        this.dept = dept;
        setLayout(new BorderLayout(10, 5));
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
        
        required_panel = new JPanel();
        optional_panel = new JPanel();
        south_panel = new JPanel();
        required_grid_panel = new JPanel();
        optional_grid_panel = new JPanel();
        required = new JComboBox[5];
        for(int i = 0; i < required.length; i++){
            required[i] =  new JComboBox(UGMain.connect.subjects);
            //required[i].addPopupMenuListener(UGMain.eventhandler);
        }
        
        optional = new JComboBox[9];
        for(int i = 0; i < optional.length; i++){
            optional[i] =  new JComboBox(UGMain.connect.subjects);
        }
        
        required_grid_panel.setLayout(new GridLayout(5, 2, 5,5));
        for(int i = 0; i < required.length; i++){
            required_grid_panel.add(new JLabel("Subject "+ (i+1)));
            required_grid_panel.add(required[i]);
            
        }
        
        optional_grid_panel.setLayout(new GridLayout(9, 2, 5,5));
        for(int i = 0; i < optional.length; i++){
            optional_grid_panel.add(new JLabel("Subject "+ (i+1)));
            optional_grid_panel.add(optional[i]);

        }
        
        required_panel.setLayout(new BorderLayout());
        required_panel.add(new JLabel("Required subjects:"), BorderLayout.NORTH);
        required_panel.add(required_grid_panel, BorderLayout.CENTER);
        required_panel.setBorder(new EmptyBorder(15,5,170,5));
        
        optionals = new JTextField(2);
        optionals.setEditable(false);
        JPanel p = new JPanel();
        p.add(new JLabel("And any"));
        p.add(optionals);
        p.add(new JLabel("subjects from :"));
        
        optional_panel.setLayout(new BorderLayout());
        optional_panel.add(p, BorderLayout.NORTH);
        optional_panel.add(optional_grid_panel, BorderLayout.CENTER);
        optional_panel.setBorder(new EmptyBorder(5,5,30,5));
        
        save = new JButton("Save Subjects");
        close = new JButton("Close");
        save.addActionListener(UGMain.eventhandler);
        close.addActionListener(UGMain.eventhandler);
        south_panel.setLayout(new BorderLayout());
        south_panel.setBorder(new EmptyBorder(0, 5, 10, 100));
        south_panel.add(close, BorderLayout.WEST);
        south_panel.add(save, BorderLayout.EAST);
        
        JTextPane pane = new JTextPane();
        pane.setText("A student applying for admission into "+ dept +" should have a minimum of credit 'C' in the following subjects:");
        pane.setEditable(false);
        pane.setOpaque(false);
        add(pane, BorderLayout.NORTH);
        add(required_panel, BorderLayout.WEST);
        add(optional_panel, BorderLayout.EAST);
        add(south_panel, BorderLayout.SOUTH);
        
        selectSaved();
        updateNumOptionals();
    }
    private void selectSaved(){
        try{
            result = statement.executeQuery("SELECT * FROM required_courses WHERE department = '"+ dept +"'");
            result.beforeFirst();
            int i = 0;
            while(result.next()){
                required[i].setSelectedItem(result.getString("subject"));
                i++;
            }
            
            result = statement.executeQuery("SELECT * FROM optional_courses WHERE department = '"+ dept +"'");
            result.beforeFirst();
            i = 0;
            while(result.next()){
                optional[i].setSelectedItem(result.getString("subject"));
                i++;
            }
            /*
            result = statement.executeQuery("SELECT * FROM departments WHERE departmentName = '"+ dept +"'");
            if(result.first())
                optionals.setText(String.valueOf(result.getInt("number_optional")));
            */
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
    }
    public void updateNumOptionals(){
        int num = 5;
        for(JComboBox box : required){
            if(box.getSelectedIndex() != 0)
                num--;
        }
        optionals.setText(String.valueOf(num));
    }
    public void doSave(){
        
        int num = Math.abs(Integer.valueOf(optionals.getText()));
        
        int count = 0;
        for(JComboBox box : optional){
            if(box.getSelectedIndex() != 0)
                count++;
        }
        
        boolean repeated = false;
        for(JComboBox box : required){
            if(box.getSelectedIndex() == 0)
                continue;
            for(JComboBox box1 : required){
                if(!box1.equals(box) && box1.getSelectedItem().equals(box.getSelectedItem()))
                    repeated = true;
            }
            for(JComboBox box1 : optional){
                if(box1.getSelectedItem().equals(box.getSelectedItem()))
                    repeated = true;
            }
        }
        for(JComboBox box : optional){
            if(box.getSelectedIndex() == 0)
                continue;
            for(JComboBox box1 : optional){
                if(!box1.equals(box) && box1.getSelectedItem().equals(box.getSelectedItem()))
                    repeated = true;
            }
        }
        if(repeated){
            JOptionPane.showMessageDialog(null, "One or more courses are repeated, please ensure their is no repetition");
        }else{
            if(count < num){
                JOptionPane.showMessageDialog(null, "Please provide enough options for the expected number of subjects in the optionals");
            }else{
                try{
                    //statement.execute("UPDATE departments SET number_optional = "+ num +" WHERE departmentName = '"+ dept +"'");

                    statement.execute("DELETE FROM required_courses WHERE department = '"+ dept +"'");
                    statement.execute("DELETE FROM optional_courses WHERE department = '"+ dept +"'");

                    for (JComboBox required1 : required) {
                        if(required1.getSelectedIndex() != 0){
                            statement.execute("INSERT INTO required_courses(department, subject) VALUES('"+ dept +"', '"+ required1.getSelectedItem().toString()
                                    +"')");
                        }
                    }

                    for (JComboBox optional1 : optional) {
                        if(optional1.getSelectedIndex() != 0){
                            statement.execute("INSERT INTO optional_courses(department, subject) VALUES('"+ dept +"', '"+ optional1.getSelectedItem().toString()
                                    +"')");
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Subjects saved!!!");

                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "SQL error occoured"+ ex.getMessage());
                }
            }
        }
    }
}
