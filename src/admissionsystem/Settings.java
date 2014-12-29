/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author SCIENCE
 */
public class Settings extends JDialog {
    JPanel contentPane;
    JTabbedPane tabs;
    OLevelPanel olevelPanel;
    UTMEPanel utmePanel;
    CutOffPanel cutoffPanel;
    public Settings(String dept){
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        olevelPanel = new OLevelPanel(dept);
        utmePanel = new UTMEPanel(dept);
        cutoffPanel = new CutOffPanel(dept);
        
        tabs.add("O-Level Requirements", olevelPanel);
        tabs.add("UTME Requirements", utmePanel);
        tabs.add("Cut-off Mark", cutoffPanel);
        
        contentPane.add(tabs, BorderLayout.CENTER);
        
        setTitle("Settings - "+ dept);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        getContentPane().add(this.contentPane);
        setSize(600, 550);
        //this.setLocation(200,200);
        setLocationRelativeTo(UGMain.contentPane);
        setResizable(false);
        //setUndecorated(true);
        //setVisible(true);
    }
    public void closeWindow(){
        dispose();
    }
}
