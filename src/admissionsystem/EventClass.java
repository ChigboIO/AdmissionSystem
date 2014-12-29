/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author SCIENCE
 */
public class EventClass implements ActionListener{

    //Settings settings = null;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(UGMain.menu.exit)){
            System.exit(0);
        }
        else if(e.getSource().equals(UGMain.menu.admitted)){
            new ViewAdmittedStudents();
        }
        else if(UGMain.menu.dept_menu_list.contains(e.getSource())){
            UGMain.settings = new Settings(e.getSource().toString());
            UGMain.settings.setVisible(true);
        }
        else if(e.getSource().equals(UGMain.settings.olevelPanel.save))
            UGMain.settings.olevelPanel.doSave();
        else if(e.getSource().equals(UGMain.settings.utmePanel.save))
            UGMain.settings.utmePanel.doSave();
        else if(e.getSource().equals(UGMain.settings.cutoffPanel.save))
            UGMain.settings.cutoffPanel.doSave();
        else if(e.getSource().equals(UGMain.settings.olevelPanel.close) || e.getSource().equals(UGMain.settings.utmePanel.close) ||
                e.getSource().equals(UGMain.settings.cutoffPanel.close))
            UGMain.settings.closeWindow();
        
        else if(UGMain.bPanel.buttons_list.contains(e.getSource())){
            UGMain.verify = new StudentVerify(e.getSource().toString());
            UGMain.verify.setVisible(true);
        }
        
        else if(e.getSource().equals(UGMain.verify.utmeVerify.check))
            UGMain.verify.utmeVerify.checkUTME();
        else if(e.getSource().equals(UGMain.verify.olevelVerify.check))
            UGMain.verify.olevelVerify.checkOlevel();
        else if(e.getSource().equals(UGMain.verify.details.save))
            UGMain.verify.details.doSave();
        
        else if(e.getSource().equals(UGMain.verify.utmeVerify.close) || e.getSource().equals(UGMain.verify.olevelVerify.close) ||
                e.getSource().equals(UGMain.verify.details.close))
            UGMain.verify.dispose();
        
        else
            JOptionPane.showMessageDialog(null, "The event source not yet handled");
    }


}
