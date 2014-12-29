package admissionsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Connect {

    public static Connection connection;
    private Statement statement;
    private ResultSet result;
    private final String serverName;
    private final String DBPort;
    private final String mydatabase;
    private final String DATABASE_URL;
    private final String USERNAME;
    private final String PASSWORD;
    String[] subjects;
    String[] deptArray;
    
    public Connect() {
        
        this.serverName = "localhost";
        this.DBPort = "3306";
        this.mydatabase = "admission_system";

        //this.DATABASE_URL = "jdbc:derby:resources/flight_scheduler;create=true";
        //this.DATABASE_URL = "jdbc:derby://"+ this.serverName +":"+ this.DBPort +"/"+ this.mydatabase;
        this.DATABASE_URL = "jdbc:mysql://" + serverName +":"+ DBPort + "/" + mydatabase; // a JDBC url
        this.USERNAME = "root";
        this.PASSWORD = "";
        try {
            //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(this.DATABASE_URL, this.USERNAME, this.PASSWORD);
            
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM subjects ORDER BY subjectName");
            result.last();
            subjects = new String[result.getRow()+1];
            subjects[0] = "";
            result.beforeFirst();
            int i = 1;
            while(result.next()){
                subjects[i] = result.getString("subjectName");
                i++;
            }

            result = statement.executeQuery("SELECT * FROM departments");
            result.last();
            deptArray = new String[result.getRow()+1];
            deptArray[0] = "";
            int j = 1;
            result.beforeFirst();
            while(result.next()){
                deptArray[j] = result.getString("departmentName");
                j++;
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "JDBC Driver not found ::: ClassNotFoundException : " + ex.getMessage());
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException occured ::: SQLException : " + ex.getMessage());
        }
    }

}
