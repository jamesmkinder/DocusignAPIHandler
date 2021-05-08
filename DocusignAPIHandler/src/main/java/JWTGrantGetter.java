import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JWTGrantGetter {
    public String getToken() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ParseException;
}
