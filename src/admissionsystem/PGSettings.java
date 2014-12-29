/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SCIENCE
 */
public class PGSettings extends JDialog {
    String dept;
    JPanel contentPane, northPanel, centerPanel, southPanel, northWestPanel, northEastPanel;
    JTextField requiredGP, screeningCut;
    JButton save, close;
    JComboBox[] allowedDepts;
    //String[] deptArray;
    Statement statement;
    ResultSet result;
    public PGSettings(String dept){
        this.dept = dept;
        contentPane = new JPanel();
        northPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();
        northWestPanel = new JPanel();
        northEastPanel = new JPanel();
        requiredGP = new JTextField(5);
        screeningCut = new JTextField(5);
        save = new JButton("Save");
        close = new JButton("Close");
        save.addActionListener(PGMain.eventhandler);
        close.addActionListener(PGMain.eventhandler);
        
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured :::"+ex.getMessage());
        }
        
        allowedDepts = new JComboBox[PGMain.allDepts.length];

        centerPanel.setLayout(new GridLayout(PGMain.allDepts.length + 1, 1,5,5));
        centerPanel.add(new JLabel("Students from the following departments can do MSc in "+dept));
        for(int j = 0; j < allowedDepts.length; j++){
            allowedDepts[j] = new JComboBox(PGMain.allDepts);
            centerPanel.add(allowedDepts[j]);
        }

        selectSaved();
        
        centerPanel.setBorder(new EmptyBorder(10,100,20,100));
        
        northWestPanel.add(new JLabel("Required CGPA :"));
        northWestPanel.add(requiredGP);
        northEastPanel.add(new JLabel("Screening Cut Off :"));
        northEastPanel.add(screeningCut);
        
        northPanel.setLayout(new BorderLayout());
        northPanel.add(northWestPanel, BorderLayout.WEST);
        northPanel.add(northEastPanel, BorderLayout.EAST);
        
        southPanel.setLayout(new BorderLayout());
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(save, BorderLayout.EAST);
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Settings - "+ dept);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        getContentPane().add(this.contentPane);
        setSize(600, 550);
        //this.setLocation(200,200);
        setLocationRelativeTo(PGMain.contentPane);
        setResizable(false);
        //setUndecorated(true);
        //setVisible(true);
    }
    private void selectSaved(){
        try{
            result = statement.executeQuery("SELECT * FROM departments WHERE departmentName = '"+ dept +"'");
            if(result.first()){
                screeningCut.setText(String.valueOf(result.getInt("PGScreeningCut")));
                requiredGP.setText(String.valueOf(result.getFloat("PG_GP")));
            }
            result = statement.executeQuery("SELECT * FROM allowedDepts WHERE department = '"+ dept +"'");
            result.beforeFirst();
            int i = 0;
            while(result.next()){
                allowedDepts[i].setSelectedItem(result.getString("sourceDepartment"));
                i++;
            }
            
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured "+ ex.getMessage());
        }
    }
    public void doSave(){
        int screening;
        float gp;
        try{
            screening = Math.abs(Integer.valueOf(screeningCut.getText()));
            gp = Float.valueOf(requiredGP.getText());
        }catch(NumberFormatException ex){
            screening = 0;
            gp = 0.0f;
        }
        if(screening <=0 || gp <= 0.0){
            JOptionPane.showMessageDialog(null, "Please supply a valid non-zero integer number for screening cut and valid non-zero float for GP");
        }else{
            boolean repeated = false;
            for(JComboBox box : allowedDepts){
                if(box.getSelectedIndex() == 0)
                    continue;
                for(JComboBox box1 : allowedDepts){
                    if(!box1.equals(box) && box1.getSelectedItem().equals(box.getSelectedItem()))
                        repeated = true;
                }
            }
            if(repeated){
                JOptionPane.showMessageDialog(null, "One or more courses are repeated, please ensure their is no repetition");
            }else{
                try{
                    statement.execute("UPDATE departments SET PG_GP = "+gp+", PGScreeningCut= "+screening+" WHERE departmentName = '"+dept+"'");
                    statement.execute("DELETE FROM allowedDepts WHERE department = '"+ dept +"'");
                    for (JComboBox required1 : allowedDepts) {
                        if(required1.getSelectedIndex() != 0){
                            statement.execute("INSERT INTO allowedDepts(department, sourceDepartment) VALUES('"+ dept +"', '"+ required1.getSelectedItem().toString()
                                    +"')");
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Allowed Departments updated successfully!!!");

                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "SQL error occoured"+ ex.getMessage());
                }
            }
        }
    }
    public void closeWindow(){
        dispose();
    }
}
