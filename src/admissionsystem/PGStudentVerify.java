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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SCIENCE
 */
public class PGStudentVerify extends JDialog {
    //public final Map<Integer, JPanel> panMap;
    private final JPanel contentPane, firstPanel, centerPanel, southPanel;
    String dept;
    JTextField screeningField, gpField;
    JComboBox source_dept;
    JButton check, close;
    PGStudentDetails details;
    Statement statement;
    ResultSet result;
    public PGStudentVerify(String dept){
        this.dept = dept;
        details = new PGStudentDetails(dept);
        contentPane = new JPanel();
        firstPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();
        screeningField = new JTextField(5);
        gpField = new JTextField(5);
        source_dept = new JComboBox(PGMain.allDepts);
        check = new JButton("Verify and Continue >>");
        close = new JButton("Close");
        check.addActionListener(PGMain.eventhandler);
        close.addActionListener(PGMain.eventhandler);
        
        
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured :::"+ex.getMessage());
        }
        
        
        centerPanel.setLayout(new GridLayout(3,2,5,5));
        centerPanel.setBorder(new EmptyBorder(100,50,200,50));
        centerPanel.add(new JLabel("Student's Source Department :"));
        centerPanel.add(source_dept);
        centerPanel.add(new JLabel("Cumulative Grade Point :"));
        centerPanel.add(gpField);
        centerPanel.add(new JLabel("Screening Score :"));
        centerPanel.add(screeningField);
        
        southPanel.setLayout(new BorderLayout());
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(check, BorderLayout.EAST);
        
        firstPanel.setLayout(new BorderLayout());
        firstPanel.add(centerPanel, BorderLayout.CENTER);
        firstPanel.add(southPanel, BorderLayout.SOUTH);
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(firstPanel, BorderLayout.CENTER);
        setTitle("Students Verification - "+ dept);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        setContentPane(this.contentPane);
        setSize(600, 550);
        //this.setLocation(200,200);
        setLocationRelativeTo(UGMain.contentPane);
        setResizable(false);
        //setUndecorated(true);
        //setVisible(true);
    }
    
    public void doCheck(){
        int screening;
        float gp;
        try{
            screening = Math.abs(Integer.valueOf(screeningField.getText()));
            gp = Float.valueOf(gpField.getText());
        }catch(NumberFormatException ex){
            screening = 0;
            gp = 0.0f;
        }
        if(screening <=0 || gp <= 0.0){
            JOptionPane.showMessageDialog(null, "Please supply a valid non-zero integer number for screening cut and valid non-zero float for GP");
        }else{
            try{
                String source = source_dept.getSelectedItem().toString();
                if(source.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select the origin department of this fellow");
                }else{
                    result = statement.executeQuery("SELECT * FROM allowedDepts WHERE department = '"+dept+"' AND sourceDepartment = '"+source+"'");
                    if(!result.first()){
                        JOptionPane.showMessageDialog(null, "Student from the selected department is not allowed to do MSc in "+dept);
                    }else{
                        result = statement.executeQuery("SELECT * FROM departments WHERE departmentName = '"+dept+"'");
                        result.first();
                        if(result.getFloat("PG_GP") > gp){
                            JOptionPane.showMessageDialog(null, "The student does not have up to the required GP to do MSc in "+dept);
                        }else{
                            if(result.getInt("PGScreeningCut") > screening)
                                JOptionPane.showMessageDialog(null, "The student does not get up to the required screening score to do MSc in "+dept);
                            else{
                                JOptionPane.showMessageDialog(null, "Congratulations, you have all it takes to do MSc in "+dept);
                                nextCheck(2);
                            }
                        }
                    }
                }
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "SQL error occoured"+ ex.getMessage());
            }
        }
    }
    
    public void nextCheck(int next){
        this.contentPane.remove(0);
        switch(next){
            case 1 :
                screeningField.setText("");
                gpField.setText("");
                source_dept.setSelectedIndex(0);
                this.contentPane.add(firstPanel, BorderLayout.CENTER);
                break;
            case 2 :
                this.contentPane.add(details, BorderLayout.CENTER);
                break;
        }
        this.contentPane.revalidate();
    }
}
