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
public class StudentDetails extends JPanel {
    String dept, regno;
    JTextField jamb_reg, department, surname, othernames, dob, state, lga;
    JComboBox sex;
    JPanel centerPanel, southPanel;
    JButton save, close;
    Statement statement;
    ResultSet result;
    public StudentDetails(String dept, String regno){
        this.dept = dept;
        this.regno = regno;
        setLayout(new BorderLayout());
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
        
        jamb_reg = new JTextField(10);
        jamb_reg.setText(regno);
        jamb_reg.setEditable(false);
        department = new JTextField(10);
        department.setText(dept);
        department.setEditable(false);
        surname = new JTextField(20);
        othernames = new JTextField(20);
        dob = new JTextField(12);
        state = new JTextField(20);
        lga = new JTextField(20);
        sex = new JComboBox(new String[]{"", "male", "female"});
        centerPanel = new JPanel();
        southPanel = new JPanel();
        save = new JButton("Save Details");
        close = new JButton("Close");
        
        centerPanel.setLayout(new GridLayout(8,2,5,5));
        centerPanel.setBorder(new EmptyBorder(10, 50, 100, 50));
        centerPanel.add(new JLabel("Jamb Reg. Number :"));
        centerPanel.add(jamb_reg);
        centerPanel.add(new JLabel("Department :"));
        centerPanel.add(department);
        centerPanel.add(new JLabel("Surname :"));
        centerPanel.add(surname);
        centerPanel.add(new JLabel("Other Names :"));
        centerPanel.add(othernames);
        centerPanel.add(new JLabel("Sex :"));
        centerPanel.add(sex);
        centerPanel.add(new JLabel("Date of Birth :"));
        centerPanel.add(dob);
        centerPanel.add(new JLabel("State of Origin :"));
        centerPanel.add(state);
        centerPanel.add(new JLabel("Local Govanment :"));
        centerPanel.add(lga);
        
        save.addActionListener(UGMain.eventhandler);
        close.addActionListener(UGMain.eventhandler);
        
        southPanel.setLayout(new BorderLayout());
        southPanel.setBorder(new EmptyBorder(5,10,5,10));
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(save, BorderLayout.EAST);
        
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public void doSave(){
        String regno = this.regno;
        String dept = this.dept;
        String surname = this.surname.getText();
        String othernames = this.othernames.getText();
        String sex = this.sex.getSelectedItem().toString();
        String dob = this.dob.getText();
        String state = this.state.getText();
        String lga = this.lga.getText();
        if(regno.isEmpty() || dept.isEmpty() || surname.isEmpty() || othernames.isEmpty() || sex.isEmpty() || dob.isEmpty() || state.isEmpty() ||
                lga.isEmpty())
            JOptionPane.showMessageDialog(null, "Some fields are are empty, please fill all the fields.");
        else{
            try{
                boolean inserted = statement.execute("INSERT INTO admitted(department, regno, surname, othernames, sex, dob, state, lga) "
                        + "VALUES('"+dept+"', '"+regno+"', '"+surname+"', '"+othernames+"', '"+sex+"', '"+dob+"', '"+state+"', '"+lga+"')");
                //if(inserted){
                    JOptionPane.showMessageDialog(null, "New Students added to the admission list.");
                    UGMain.verify.nextCheck(1, "");
                //}else
                //    JOptionPane.showMessageDialog(null, "ERROR ::: could not add this student to the DB.");
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "SQL error occoured ::: "+ex.getMessage());
            }
        }
    }
}
