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
public class UTMEVerifyPanel extends JPanel {

    String dept;
    JPanel northPanel, centerPanel, southPanel;
    JComboBox sub1, sub2, sub3, sub4;
    JTextField jamb_reg, ume_score1, ume_score2, ume_score3, ume_score4, pume_score1, pume_score2, pume_score3, pume_score4;
    JButton check, close;
    Statement statement;
    ResultSet result;

    public UTMEVerifyPanel(String dept) {
        this.dept = dept;

        try {
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }

        northPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();
        sub1 = new JComboBox(UGMain.connect.subjects);
        sub2 = new JComboBox(UGMain.connect.subjects);
        sub3 = new JComboBox(UGMain.connect.subjects);
        sub4 = new JComboBox(UGMain.connect.subjects);
        jamb_reg = new JTextField(10);
        ume_score1 = new JTextField(5);
        ume_score2 = new JTextField(5);
        ume_score3 = new JTextField(5);
        ume_score4 = new JTextField(5);
        pume_score1 = new JTextField(5);
        pume_score2 = new JTextField(5);
        pume_score3 = new JTextField(5);
        pume_score4 = new JTextField(5);
        check = new JButton("Check and Continue>>");
        close = new JButton("Close");

        centerPanel.setLayout(new GridLayout(5, 3, 5, 5));
        centerPanel.add(new JLabel("Subjects"));
        centerPanel.add(new JLabel("UTME Scores"));
        centerPanel.add(new JLabel("PUTME Scores"));
        centerPanel.add(sub1);
        centerPanel.add(ume_score1);
        centerPanel.add(pume_score1);
        centerPanel.add(sub2);
        centerPanel.add(ume_score2);
        centerPanel.add(pume_score2);
        centerPanel.add(sub3);
        centerPanel.add(ume_score3);
        centerPanel.add(pume_score3);
        centerPanel.add(sub4);
        centerPanel.add(ume_score4);
        centerPanel.add(pume_score4);
        centerPanel.setBorder(new EmptyBorder(5, 100, 250, 100));

        check.addActionListener(UGMain.eventhandler);
        close.addActionListener(UGMain.eventhandler);

        northPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
        northPanel.add(new JLabel("JAMB Registration"));
        northPanel.add(jamb_reg);

        southPanel.setLayout(new BorderLayout());
        southPanel.add(close, BorderLayout.WEST);
        southPanel.add(check, BorderLayout.EAST);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

    }

    public void checkUTME() {
        String regno = jamb_reg.getText();
        String sub1 = this.sub1.getSelectedItem().toString();
        String sub2 = this.sub2.getSelectedItem().toString();
        String sub3 = this.sub3.getSelectedItem().toString();
        String sub4 = this.sub4.getSelectedItem().toString();
        String ume_sc1 = this.ume_score1.getText();
        String ume_sc2 = this.ume_score2.getText();
        String ume_sc3 = this.ume_score3.getText();
        String ume_sc4 = this.ume_score4.getText();
        String pume_sc1 = this.pume_score1.getText();
        String pume_sc2 = this.pume_score2.getText();
        String pume_sc3 = this.pume_score3.getText();
        String pume_sc4 = this.pume_score4.getText();
        int utme;
        int putme;

        if (regno.isEmpty() || sub1.isEmpty() || sub2.isEmpty() || sub3.isEmpty() || sub4.isEmpty() || ume_sc1.isEmpty() || ume_sc2.isEmpty()
                || ume_sc3.isEmpty() || ume_sc4.isEmpty() || pume_sc1.isEmpty() || pume_sc2.isEmpty() || pume_sc3.isEmpty() || pume_sc4.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error :: One or more fields are not filled or selected");
        } else {
            try {
                utme = Integer.valueOf(ume_sc1) + Integer.valueOf(ume_sc2) + Integer.valueOf(ume_sc3) + Integer.valueOf(ume_sc4);
                putme = Integer.valueOf(pume_sc1) + Integer.valueOf(pume_sc2) + Integer.valueOf(pume_sc3) + Integer.valueOf(pume_sc4);
                
                result = statement.executeQuery("SELECT * FROM utme_required WHERE department = '" + dept + "'");
                result.last();
                int num_required = result.getRow();
                boolean allcourses = true;
                result.beforeFirst();
                while (result.next()) {
                    if (!result.getString("subject").equals(sub1) && !result.getString("subject").equals(sub2)
                            && !result.getString("subject").equals(sub3) && !result.getString("subject").equals(sub4)) {
                        allcourses = false;
                        break;
                    }
                }
                if (allcourses) {
                    int num_optional = 4 - num_required;
                    result = statement.executeQuery("SELECT * FROM utme_optional WHERE department = '" + dept + "' AND (subject = '" + sub1 + "'"
                            + " OR " + "subject = '" + sub2 + "' OR subject = '" + sub3 + "' OR subject = '" + sub4 + "')");
                    result.last();
                    if (result.getRow() < num_optional) {
                        allcourses = false;
                    }
                }
                if (allcourses) {
                    
                    this.result = this.statement.executeQuery("SELECT utme_cut_off, putme_cut_off FROM departments WHERE departmentName = '" + dept + "'");

                    if (result.first()) {
                        if (utme >= (this.result.getInt("utme_cut_off"))) {
                            if (((utme + putme) / 2) >= (this.result.getInt("putme_cut_off"))) {
                                //all is well, now call the olevel verification
                                UGMain.verify.nextCheck(2, regno);
                            } else {
                                JOptionPane.showMessageDialog(null, "The average of UTME and PUTME does not reach the departmental cutoff mark",
                                        "Not Qualified", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The total UTME score does not reach the required UTME cut off mark", "Not Qualified",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The UTME and PUTME cutoff mark of this department has not been set.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, you did not take the courses required for this department");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "One or more of the score fields has a non number value");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL error occoured " + ex.getMessage());
            }

        }
    }
}
