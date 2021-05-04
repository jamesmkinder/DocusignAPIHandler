import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.Envelope;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.sql.SQLException;

public interface EmailRequest {
    public void requestEmail() throws SQLException, ClassNotFoundException, IOException, OAuthSystemException, ApiException;
}
