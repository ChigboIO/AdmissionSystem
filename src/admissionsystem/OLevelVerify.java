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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SCIENCE
 */
public class OLevelVerify extends JPanel {
    String dept;
    String regno;
    JPanel northPanel, centerPanel, southPanel;
    JButton check, close;
    JTextField jamb_reg;
    JComboBox[] subjects;
    JComboBox[] grades;
    String[] grade_letters;
    Statement statement;
    ResultSet result;
    public OLevelVerify(String dept, String regno){
        this.dept = dept;
        this.regno = regno;
        northPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();
        setLayout(new BorderLayout(10, 5));
        
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
        
        grade_letters = new String[]{"", "A1", "B2", "B3", "C4", "C5", "C6", "D7", "E8", "F9"};
        subjects = new JComboBox[9];
        grades = new JComboBox[9];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] =  new JComboBox(UGMain.connect.subjects);
            grades[i] =  new JComboBox(grade_letters);
        }
        
        centerPanel.setLayout(new GridLayout(10, 2, 5,5));
        centerPanel.setBorder(new EmptyBorder(5,20,5,20));
        centerPanel.add(new JLabel("Subjects"));
        centerPanel.add(new JLabel("Grades"));
        for(int i = 0; i < subjects.length; i++){
            centerPanel.add(subjects[i]);
            centerPanel.add(grades[i]);
        }
        
        check = new JButton("Check and Continue");
        close = new JButton("Close");
        check.addActionListener(UGMain.eventhandler);
        close.addActionListener(UGMain.eventhandler);
        southPanel.setLayout(new BorderLayout());
        southPanel.setBorder(new EmptyBorder(0, 5, 10, 100));
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(check, BorderLayout.EAST);
        
        jamb_reg = new JTextField(10);
        jamb_reg.setText(regno);
        jamb_reg.setEditable(false);
        northPanel.add(new JLabel("Jamb Registration Number :"));
        northPanel.add(jamb_reg);
        
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public void checkOlevel(){
        String[] subjects = new String[this.subjects.length];
        String[] grades = new String[this.grades.length];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = this.subjects[i].getSelectedItem().toString();
            grades[i] = this.grades[i].getSelectedItem().toString();
        }
        
        boolean repeated = false;
        for(JComboBox box : this.subjects){
            if(box.getSelectedIndex() == 0)
                continue;
            for(JComboBox box1 : this.subjects){
                if(!box1.equals(box) && box1.getSelectedItem().equals(box.getSelectedItem()))
                    repeated = true;
            }
        }
        if(repeated){
            JOptionPane.showMessageDialog(null, "One or more courses are repeated, please ensure their is no repetition");
        }else{
            try{
                result = statement.executeQuery("SELECT * FROM required_courses WHERE department = '"+ dept +"'");
                result.last();
                int num_required = result.getRow();
                boolean allcourses = true;
                result.beforeFirst();
                while(result.next()){
                    boolean b = false;
                    for(int i = 0; i < subjects.length; i++){
                        if(result.getString("subject").equals(subjects[i]) && grades[i].compareTo("C6") <= 0 && !grades[i].isEmpty()){
                            b = true;
                            break;
                        }
                    }
                    if(!b){
                        allcourses = false;
                        break;
                    }
                }
                if(allcourses){
                    int num_optional = 5 - num_required;
                    result = statement.executeQuery("SELECT * FROM optional_courses WHERE department = '"+ dept +"'");
                    result.beforeFirst();
                    while(result.next()){
                        for(int i = 0; i < subjects.length; i++){
                            if(result.getString("subject").equals(subjects[i]) && grades[i].compareTo("C6") <= 0 && !grades[i].isEmpty()){
                                num_optional--;
                                break;
                            }
                        }
                    }
                    if(num_optional > 0)
                        allcourses = false;   
                }
                if(allcourses){
                    //JOptionPane.showMessageDialog(null, "Congratulations!!! you made it.");
                    UGMain.verify.nextCheck(3, regno);
                }else{
                    JOptionPane.showMessageDialog(null, "Sorry, you did not take or credit the 5 subjects required for this department");
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "SQL error ::: "+ex.getMessage());
            }
        }
    }
}
