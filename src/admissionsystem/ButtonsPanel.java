/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author SCIENCE
 */
public class ButtonsPanel extends JPanel {
    MyButton[] buttons;
    List buttons_list;
    Statement statement;
    ResultSet result;
    public ButtonsPanel(){
        setLayout(new FlowLayout());
        buttons_list = new ArrayList();
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM departments");
            result.last();
            buttons = new MyButton[result.getRow()];
            int i = 0;
            result.beforeFirst();
            while(result.next()){
                buttons[i] = new MyButton(result.getString("departmentName"));
                buttons[i].addActionListener(UGMain.eventhandler);
                buttons_list.add(buttons[i]);
                this.add(buttons[i]);
                i++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured");
        }
        
    }
    
    private class MyButton extends JButton {
        String text;
        public MyButton(String text){
            super(text);
            this.text = text;
            super.setPreferredSize(new Dimension(250, 150));
        }
        @Override
        public String toString(){
            return text;
        }
    }
}
