import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class AppConfiguration {
    private static AppConfiguration appConfiguration;
    private int reportPK;
    private String fileName;
    private String appName;
    private String ik;
    private String secretKey;
    private String redirectURI;
    private String userID;
    private String APIAccountID;
    private String basePath;
    private String rsaPublic;
    private String rsaPrivate;
    private String database;

    private AppConfiguration(){}

    public AppConfiguration(String appName, int reportPK, String fileName) {
        if(appConfiguration == null) {
            appConfiguration = new AppConfiguration();
            configure(appName, reportPK, fileName, appConfiguration);
        }
    }

    private void configure(String appName, int reportPK, String fileName, AppConfiguration appConfiguration) {

        appConfiguration.setAppName(appName);
        appConfiguration.setReportPK(reportPK);
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(appName + ".JSON");
        if (inputStream == null){
            JOptionPane.showMessageDialog(null, "Could not locate the JSON file needed to configure this application.  Please notify James Kinder if you get this error.");
        }
        String json = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    try {
        Object obj = parser.parse(json);
        JSONObject jsonObject = (JSONObject) obj;
        appConfiguration.setIK((String) jsonObject.get("IK"));
        appConfiguration.setSecretKey((String) jsonObject.get("SecretKey"));
        appConfiguration.setRedirectURI((String) jsonObject.get("Redirect"));
        appConfiguration.setUserID((String) jsonObject.get("APIUsername"));
        appConfiguration.setAPIAccountID((String) jsonObject.get("AccountID"));
        appConfiguration.setBasePath((String) jsonObject.get("BasePath"));
        appConfiguration.setRsaPrivate((String) jsonObject.get("RSAPrivate"));
        appConfiguration.setRsaPublic((String) jsonObject.get("RSAPublic"));
        appConfiguration.setDatabase((String) jsonObject.get("Database"));
        appConfiguration.setFileName(fileName);
    } catch (ParseException e){
        JOptionPane.showMessageDialog(null, "The JSON file used to configure this application could not be parsed.  Please update the JSON file and try again.");
        System.exit(1);
    }

    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private void setAPIAccountID(String accountID) {
        this.APIAccountID = accountID;
    }

    private void setUserID(String apiUsername) {
        this.userID = apiUsername;
    }

    private void setRedirectURI(String redirect) {
        this.redirectURI = redirect;
    }

    private void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private void setIK(String ik) {
        this.ik = ik;
    }

    private void setReportPK(int reportPK) {
        this.reportPK = reportPK;
    }

    private void setAppName(String appName) {
        this.appName = appName;
    }

    public void setRsaPrivate(String rsaPrivate) {
        this.rsaPrivate = rsaPrivate;
    }

    public static String getRsaPrivate() {
        return appConfiguration.rsaPrivate;
    }

    private void setDatabase(String database){
        this.database = database;
    }

    public static String getDatabase(){
        return appConfiguration.database;
    }

    private void setRsaPublic(String rsaPublic){
        this.rsaPublic = rsaPublic;
    }

    public static String getRsaPublic(){
        return appConfiguration.rsaPublic;
    }

    public static String getIk() {
        return appConfiguration.ik;
    }

    public static String getSecretKey() {
        return appConfiguration.secretKey;
    }

    public static String getRedirectURI() { return appConfiguration.redirectURI; }

    public static String getUserID() {
        return appConfiguration.userID;
    }

    public static String getAPIAccountID() { return appConfiguration.APIAccountID; }

    public static String getBasePath() {
        return appConfiguration.basePath;
    }

    public static String getAppName() {
        return appConfiguration.appName;
    }

    public static int getReportPK() {
        return appConfiguration.reportPK;
    }

    public static String getFileName() {
        return appConfiguration.fileName;
    }
}
