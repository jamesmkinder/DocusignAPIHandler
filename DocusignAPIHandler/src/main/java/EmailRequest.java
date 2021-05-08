import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public interface EmailRequest {
    void requestEmail() throws SQLException, ClassNotFoundException, IOException, OAuthSystemException, ApiException, InvalidKeySpecException, NoSuchAlgorithmException;
}
