/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCIENCE
 */
public class PGViewAdmittedStudents extends JDialog {
    JTable table;
    Statement statement;
    ResultSet result;
    JPanel contentPane;
    JScrollPane scroll;
    public PGViewAdmittedStudents(){
        contentPane = new JPanel();
        String[] heading = null;
        String[][] data = null;
        try{
            statement = Connect.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM PGadmitted");
            result.last();
            heading = new String[]{"Department", "Surname", "Other Names", "Sex", "Date of Birth", "State", "LGA" };
            data = new String[result.getRow()][7];

            result.beforeFirst();
            int i = 0;
            while(result.next())
            {
                data[i][0] = result.getString("department");
                data[i][1] = result.getString("surname");
                data[i][2] = result.getString("othernames");
                data[i][3] = result.getString("sex");
                data[i][4] = result.getString("dob");
                data[i][5] = result.getString("state");
                data[i][6] = result.getString("lga");

                i++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL error occoured :::"+ex.getMessage());
        }
        
        DefaultTableModel model = new DefaultTableModel(data, heading);
        table = new JTable(model);
        table.setRowHeight(30);
        scroll = new JScrollPane(table);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(scroll, BorderLayout.CENTER);
        setTitle("PG Admitted Students' List");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        
        setContentPane(this.contentPane);
        setSize(700, 550);
        //this.setLocation(200,200);
        setLocationRelativeTo(UGMain.contentPane);
        setResizable(false);
        //setUndecorated(true);
        setVisible(true);
    }
}
