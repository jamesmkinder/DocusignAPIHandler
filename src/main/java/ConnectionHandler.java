import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionHandler {

    public static ResultSet runSQL(String SQL) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(AppConfiguration.getDatabase());
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            conn.close();
            return rs;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "A fatal error has occurred: no suitable driver was found to establish the JDBC connection with the database.  Please contact James Kinder if you receive this error.");
            System.exit(1);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A fatal error has occurred: this application made a query to the database that contained an error. This could be because of a change in the structure of the backend database, or " +
                    "because some required fields are missing data. Please correct the error and try again.");
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}