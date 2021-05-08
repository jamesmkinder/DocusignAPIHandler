import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.bouncycastle.util.io.pem.PemReader;

public class JWT {

    private String privateKey;
    private String iss;
    private String sub;
    private String aud;
    private String scope;


    public JWT(String privateKey, String iss, String sub, String aud, String scope) {
        this.privateKey = privateKey;
        this.iss = iss;
        this.sub = sub;
        this.aud = aud;
        this.scope = scope;
    }

    public String encode() {

        String retStr = null;


        Map<String, Object> headers = Map.of("typ", "JWT", "alg", "RS256");
        Claims claims = Jwts.claims();
        claims.put("iss", iss);
        claims.put("sub", sub);
        claims.put("aud", aud);
        claims.put("iat", System.currentTimeMillis()/1000);
        claims.put("exp", (System.currentTimeMillis()/1000) + 3600);
        claims.put("scope", scope);
        // strip the headers
        privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("\\s+","");

        byte[] encodedKey = Base64.getDecoder().decode(privateKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);

        try {

            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);

            retStr = Jwts.builder().setHeader(headers).setClaims(claims).signWith(privKey).compact();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return retStr;
    }
}