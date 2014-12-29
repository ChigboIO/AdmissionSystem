/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author SCIENCE
 */
public class MenuBar extends JMenuBar {
    JMenu file, settings, view;
    MyMenuItem exit, admitted;
    MyMenuItem[] dept_menu;
    List dept_menu_list;
    Statement statement;
    ResultSet result;
    public MenuBar(){
        dept_menu_list = new ArrayList();
        file = new JMenu("File");
        view = new JMenu("View");
        settings = new JMenu("Settings");
        
        exit = new MyMenuItem("Exit");
        exit.addActionListener(UGMain.eventhandler);
        admitted = new MyMenuItem("Admitted Students");
        admitted.addActionListener(UGMain.eventhandler);
        
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM departments");
            result.last();
            dept_menu = new MyMenuItem[result.getRow()];
            int i = 0;
            result.beforeFirst();
            while(result.next()){
                dept_menu[i] = new MyMenuItem(result.getString("departmentName"));
                dept_menu[i].addActionListener(UGMain.eventhandler);
                dept_menu_list.add(dept_menu[i]);
                settings.add(dept_menu[i]);
                i++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }

        file.add(exit);
        view.add(admitted);
        
        add(file);
        add(settings);
        add(view);
    }
    
    private class MyMenuItem extends JMenuItem{
        private final String item;
        public MyMenuItem(String item){
            super(item);
            this.item = item;
        }
        @Override
        public String toString(){
            return item;
        }
    }
}
