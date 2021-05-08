import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class RequestFactory{

    public void requestEmail() throws SQLException, ClassNotFoundException, IOException, ApiException, OAuthSystemException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (AppConfiguration.getAppName().equals("WeldProcessReport")){
            EmailRequest weldProcessReportRequest = new WeldProcessReportRequest();
            weldProcessReportRequest.requestEmail();
        }
    }
}
