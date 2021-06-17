import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WeldProcessReportJWTGrantGetter implements JWTGrantGetter{

    public WeldProcessReportJWTGrantGetter() {}

    @Override
    public String getToken() {
        String accessToken = null;
        try {
            JWT jwt = new JWT(AppConfiguration.getRsaPrivate(), AppConfiguration.getIk(), AppConfiguration.getUserID(), AppConfiguration.getBasePath(), "signature impersonation extended");

            String jwtString = jwt.encode();

            URL url = new URL("https://account.docusign.com/oauth/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
            arguments.put("assertion", jwtString);
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

            connection.setFixedLengthStreamingMode(out.length);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.connect();
            try (OutputStream os = connection.getOutputStream()) {
                os.write(out);
            }
            String jsonResponseString = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(jsonResponseString);

                accessToken = (String) jsonObject.get("access_token");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(accessToken);
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "An error has occurred: the HTTP post request has failed.");
            System.exit(1);
        }
        return accessToken;
    }
}
