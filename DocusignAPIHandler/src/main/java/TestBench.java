import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class TestBench {
    //Proof of concept test code.

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, OAuthSystemException, ApiException, InvalidKeySpecException, NoSuchAlgorithmException {

        new AppConfiguration("WeldProcessReport", 699, "G:\\codeRepository\\WeldLoggerProProjectSC Weld Reports - -  -  - Geet(699).pdf");
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}