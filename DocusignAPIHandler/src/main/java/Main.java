import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://G://codeRepository//WeldLoggerPro Project//WeldMaterialControlLog_be.accdb");

        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM WELD_JOB WHERE JOB_ID = " + args[0]);
        rs.next();

        System.out.println(rs.getString("WELDER_CLOCK_NUM"));

        conn.close();
    }
}
