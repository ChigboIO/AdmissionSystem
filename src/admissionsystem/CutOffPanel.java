/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SCIENCE
 */
public class CutOffPanel extends JPanel {
    String dept;
    JPanel centerPanel, southPanel;
    JTextField utmeCutOff, putmeCutOff;
    JButton save, close;
    Statement statement;
    ResultSet result;
    public CutOffPanel(String dept){
        this.dept = dept;
        setLayout(new BorderLayout());
        
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
        
        centerPanel = new JPanel();
        southPanel = new JPanel();
        utmeCutOff = new JTextField(5);
        putmeCutOff = new JTextField(5);
        save = new JButton("Save Cutoff Marks");
        close = new JButton("Close");
        
        centerPanel.setLayout(new GridLayout(2,2,5,5));
        centerPanel.add(new JLabel("UTME Cutoff Mark : "));
        centerPanel.add(utmeCutOff);
        centerPanel.add(new JLabel("Average of UTME and PUTME : "));
        centerPanel.add(putmeCutOff);
        centerPanel.setBorder(new EmptyBorder(50,100,350,100));
        
        save.addActionListener(UGMain.eventhandler);
        close.addActionListener(UGMain.eventhandler);
        
        southPanel.setLayout(new BorderLayout());
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(save, BorderLayout.EAST);
        
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        initValues();
    }
    
    private void initValues(){
        try{
            result = statement.executeQuery("SELECT utme_cut_off, putme_cut_off FROM departments WHERE departmentName = '"+ dept +"'");
            if(result.first()){
                utmeCutOff.setText(String.valueOf(result.getInt("utme_cut_off")));
                putmeCutOff.setText(String.valueOf(result.getInt("putme_cut_off")));
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
    }
    
    public void doSave(){
        int utme;
        int putme;
        try{
            utme = Math.abs(Integer.valueOf(utmeCutOff.getText()));
            putme = Math.abs(Integer.valueOf(putmeCutOff.getText()));
        } catch(NumberFormatException ex){
            utme = 0;
            putme = 0;
        }
        
        try{
            statement.execute("UPDATE departments SET utme_cut_off = "+ utme +" WHERE departmentName = '"+ dept +"'");
            statement.execute("UPDATE departments SET putme_cut_off = "+ putme +" WHERE departmentName = '"+ dept +"'");
            
            JOptionPane.showMessageDialog(null, "CutOff Marks saved!!!");
            
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
    }
}
