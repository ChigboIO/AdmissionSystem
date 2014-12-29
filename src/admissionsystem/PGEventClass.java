/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author SCIENCE
 */
public class PGEventClass implements ActionListener, PopupMenuListener, WindowListener{

    //Settings settings = null;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(PGMain.menu.exit)){
            System.exit(0);
        }
        else if(e.getSource().equals(PGMain.menu.admitted)){
            new PGViewAdmittedStudents();
        }
        else if(PGMain.menu.dept_menu_list.contains(e.getSource())){
            PGMain.settings = new PGSettings(e.getSource().toString());
            PGMain.settings.setVisible(true);
        }
        else if(e.getSource().equals(PGMain.settings.save))
            PGMain.settings.doSave();
        else if(e.getSource().equals(PGMain.settings.close))
            PGMain.settings.closeWindow();
        
        else if(PGMain.bPanel.buttons_list.contains(e.getSource())){
            PGMain.verify = new PGStudentVerify(e.getSource().toString());
            PGMain.verify.setVisible(true);
        }
        
        else if(e.getSource().equals(PGMain.verify.check))
            PGMain.verify.doCheck();
        else if(e.getSource().equals(PGMain.verify.details.save))
            PGMain.verify.details.doSave();
        
        else if(e.getSource().equals(PGMain.verify.close) || e.getSource().equals(PGMain.verify.details.close))
            PGMain.verify.dispose();
        
        else
            JOptionPane.showMessageDialog(null, "The event source not yet handled");
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        //do combo selection task here
        //PGMain.settings.olevelPanel.updateNumOptionals();
        //PGMain.settings.utmePanel.updateNumOptionals();
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //noting happens
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //something happens here
        //PGMain.control.updater();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //or here
        JOptionPane.showMessageDialog(null, "closing window");
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
