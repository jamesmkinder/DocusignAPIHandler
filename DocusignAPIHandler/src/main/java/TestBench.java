import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestBench {
    //Proof of concept test code.

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException, OAuthSystemException, ApiException, InvalidKeySpecException, NoSuchAlgorithmException {

/*
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://G://codeRepository//WeldLoggerProProject//WeldMaterialControlLog_be.accdb");
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM WELDERS WHERE WELDER_CLOCK_NUM = 10006167");


        rs.next();
        System.out.println(rs.getString("First_Init") + " " + rs.getString("Last_Name"));
        conn.close();
 */

        new AppConfiguration("WeldProcessReport", 698, "G:/codeRepository/WeldLoggerProProjectSC Weld Reports - -  - 23 - Disk(698)cc050420210000.pdf");
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}