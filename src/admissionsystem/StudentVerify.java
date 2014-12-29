/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author SCIENCE
 */
public class StudentVerify extends JDialog {
    //public final Map<Integer, JPanel> panMap;
    private final JPanel contentPane;
    UTMEVerifyPanel utmeVerify;
    OLevelVerify olevelVerify;
    StudentDetails details;
    String dept;
    public StudentVerify(String dept){
        this.dept = dept;
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        utmeVerify = new UTMEVerifyPanel(dept);
        olevelVerify = new OLevelVerify(dept, "");
        details = new StudentDetails(dept, "");
        
        contentPane.add(utmeVerify, BorderLayout.CENTER);
        
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
    
    public void nextCheck(int next, String regno){
        this.contentPane.remove(0);
        switch(next){
            case 1 :
                utmeVerify = new UTMEVerifyPanel(dept);
                this.contentPane.add(utmeVerify, BorderLayout.CENTER);
                break;
            case 2 :
                olevelVerify = new OLevelVerify(dept, regno);
                this.contentPane.add(olevelVerify, BorderLayout.CENTER);
                break;
            case 3 :
                details = new StudentDetails(dept, regno);
                this.contentPane.add(details, BorderLayout.CENTER);
                break;
        }
        this.contentPane.revalidate();
    }
}
