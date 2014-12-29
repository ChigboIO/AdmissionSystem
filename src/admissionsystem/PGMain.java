/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author SCIENCE
 */
public class PGMain extends JFrame {

    static String[] courses;
    static String[] allDepts;
    static JPanel contentPane;
    static Connect connect;
    static PGMenuBar menu;
    static PGSettings settings;
    static PGStudentVerify verify;
    static PGButtonsPanel bPanel;
    static PGEventClass eventhandler;
    public PGMain() {
        super("Postgraduate Admission Guide");
        initComponents();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(bPanel, BorderLayout.CENTER);
        
        setJMenuBar(menu);
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,600);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    private void initComponents(){
        connect = new Connect();
        
        String[] deptArray = new String[]{"Eletrical Engineering", "mechanical Engineering", "Electronics Engineering", "Civil Engineering"};
        
        int i = PGMain.connect.deptArray.length;
        
        allDepts = new String[i + deptArray.length];
        for(int k = 0; k < i; k++){
            allDepts[k] = PGMain.connect.deptArray[k];
        }
        for(int k = i; k < allDepts.length; k++){
            allDepts[k] = deptArray[k - i];
        }
        
        eventhandler = new PGEventClass();
        contentPane = new JPanel();
        menu = new PGMenuBar();
        bPanel = new PGButtonsPanel();
        settings = new PGSettings("default");
        verify = new PGStudentVerify("default");
        courses = new String[]{"", "English", "Mathematics", "Biology", "Chemistry", "Physics", "Agricultural Science", "Economics"};
        
        
        
    }
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Error setting UI to JTatoo -> TextureLookAndFeel ::: "+ ex.getMessage());
        }
        new Main();
    }
    */
}
