import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class WeldProcessReportRequest implements EmailRequest{

    private static final String WELDER_ANCHOR_OFFSET_Y = "10";
    private static final String WELDER_ANCHOR_OFFSET_X = "20";
    private static final String SUPERVISOR_ANCHOR_OFFSET_Y = "10";
    private static final String SUPERVISOR_ANCHOR_OFFSET_X = "20";

    @Override
    public void requestEmail() throws SQLException, ClassNotFoundException, IOException, ApiException, InvalidKeySpecException, NoSuchAlgorithmException {

        ResultSet rsWelder = ConnectionHandler.runSQL("SELECT FIRST_INIT & ', ' & LAST_NAME, EMAIL, SUPERVISOR FROM WELDERS INNER JOIN WELD_JOB ON WELD_JOB.WELDER_CLOCK_NUM = WELDERS.WELDER_CLOCK_NUM WHERE JOB_ID = " + AppConfiguration.getReportPK());
        rsWelder.next();
        Signer welder = new Signer();
        welder.setName(rsWelder.getString(1));
        welder.setEmail(rsWelder.getString(2));
        welder.setRecipientId("1");
        welder.setRoutingOrder("1");
        Tabs welderTabs = new Tabs();
        SignHere welderSignature = new SignHere();
        welderSignature.setAnchorXOffset(WELDER_ANCHOR_OFFSET_X);
        welderSignature.setAnchorYOffset(WELDER_ANCHOR_OFFSET_Y);
        welderTabs.addSignHereTabsItem(welderSignature);
        welder.setTabs(welderTabs);

        ResultSet rsSupervisor = ConnectionHandler.runSQL("SELECT FIRST_INIT & ', ' & LAST_NAME, EMAIL FROM WELDERS WHERE WELDER_CLOCK_NUM = " + rsWelder.getString(3));
        rsSupervisor.next();
        Signer supervisor = new Signer();
        supervisor.setName(rsSupervisor.getString(1));
        supervisor.setEmail(rsSupervisor.getString(2));
        supervisor.setRecipientId("2");
        supervisor.setRoutingOrder("2");
        Tabs supervisorTabs = new Tabs();
        SignHere supervisorSignature = new SignHere();
        supervisorSignature.setAnchorYOffset(SUPERVISOR_ANCHOR_OFFSET_Y);
        supervisorSignature.setAnchorXOffset(SUPERVISOR_ANCHOR_OFFSET_X);
        supervisorTabs.addSignHereTabsItem(supervisorSignature);
        supervisor.setTabs(supervisorTabs);
        Signer[] signers = {welder, supervisor};

        Document weldProcessReport = EnvelopeHelpers.createDocumentFromFile(AppConfiguration.getFileName(), AppConfiguration.getAppName(), "1");
        EnvelopeDefinition envelope = new EnvelopeDefinition();
        envelope.setEmailSubject("Please Sign This Weld Process Report Docusign Document");
        envelope.setDocuments(Collections.singletonList(weldProcessReport));
        envelope.setRecipients(EnvelopeHelpers.createRecipients(signers));
        envelope.setStatus("sent");

        ApiClient apiClient = new ApiClient(AppConfiguration.getBasePath());
        JWTGrantGetter jwt = new WeldProcessReportJWTGrantGetter();
        apiClient.setAccessToken(jwt.getToken(), (long) 3600);
        apiClient.addDefaultHeader("User-Agent", "Swagger-Codegen/3.11.0-RC2/java");
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
        EnvelopeSummary results = envelopesApi.createEnvelope(AppConfiguration.getAPIAccountID(), envelope);

    }
}