import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeldProcessReportJWTGrantGetter implements JWTGrantGetter{
    private static String accessToken;

    public WeldProcessReportJWTGrantGetter() {}

    @Override
    public String getToken() throws IOException {
        if (accessToken == null){

            JWT jwt = new JWT(AppConfiguration.getRsaPrivate(), AppConfiguration.getIk(), AppConfiguration.getUserID(), AppConfiguration.getBasePath(), "signature impersonation extended");

            String jwtString = jwt.encode();

            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://account-d.docusign.com/oauth/token");
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer"));
            params.add(new BasicNameValuePair("assertion", jwtString));
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
            HttpResponse response = httpClient.execute(httpPost);



            System.out.println(accessToken);



            //new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));















        }
        return accessToken;
    }
}
