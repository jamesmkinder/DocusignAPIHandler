import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionHandler {

    public static ResultSet runSQL(String SQL) throws SQLException, ClassNotFoundException {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection(AppConfiguration.getDatabase());
        ResultSet rs = conn.createStatement().executeQuery(SQL);
        conn.close();
        return rs;
    }
}
