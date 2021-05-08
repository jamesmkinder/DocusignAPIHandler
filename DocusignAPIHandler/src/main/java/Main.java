import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, OAuthSystemException, ApiException, InvalidKeySpecException, NoSuchAlgorithmException {

        new AppConfiguration(args[0], Integer.parseInt(args[1]), args[2]);
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();


    }
}