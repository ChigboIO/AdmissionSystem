/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package admissionsystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author SCIENCE
 */
public class Main extends JFrame implements ActionListener {
    JButton ugButton, pgButton;
    JPanel contentPane;
    public Main() {
        super("Admission Guide");
        initComponents();
        
        ugButton.addActionListener(this);
        pgButton.addActionListener(this);
        
        contentPane.setLayout(new GridLayout(2,1,5,5));
        contentPane.add(ugButton);
        contentPane.add(pgButton);
        
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    private void initComponents(){
        contentPane = new JPanel();
        ugButton = new JButton("Undergraduate");
        pgButton = new JButton("Post Graduate");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(ugButton)){
            this.dispose();
            new UGMain();
        }else if(e.getSource().equals(pgButton)){
            this.dispose();
            new PGMain();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Error setting UI to JTatoo -> TextureLookAndFeel ::: "+ ex.getMessage());
        }
        new Main();
    }

}
