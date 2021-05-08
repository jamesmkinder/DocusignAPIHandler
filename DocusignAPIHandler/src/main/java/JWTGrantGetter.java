import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JWTGrantGetter {
    String getToken() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException;
}
