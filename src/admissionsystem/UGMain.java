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
public class UGMain extends JFrame {

    static String[] courses;
    static JPanel contentPane;
    static Connect connect;
    static MenuBar menu;
    static Settings settings;
    static StudentVerify verify;
    static ButtonsPanel bPanel;
    static EventClass eventhandler;
    public UGMain() {
        super("Undergraduate Admission Guide");
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
        eventhandler = new EventClass();
        contentPane = new JPanel();
        menu = new MenuBar();
        bPanel = new ButtonsPanel();
        settings = new Settings("default");
        verify = new StudentVerify("default");
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
