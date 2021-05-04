package com.docusign.DocusignAPIHandler.src.main.java;

import com.docusign.core.model.DoneExample;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.auth.OAuth;
import com.docusign.esign.model.*;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;


import java.io.IOException;
import java.net.http.HttpHeaders;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import static com.docusign.controller.eSignature.examples.AbstractEsignatureController.createEnvelopesApi;

public class WeldProcessReportRequest implements EmailRequest{

    private static final String WELDER_ANCHOR_OFFSET_Y = "10";
    private static final String WELDER_ANCHOR_OFFSET_X = "20";
    private static final String SUPERVISOR_ANCHOR_OFFSET_Y = "10";
    private static final String SUPERVISOR_ANCHOR_OFFSET_X = "20";

    @Override
    public void requestEmail() throws SQLException, ClassNotFoundException, IOException, OAuthSystemException, ApiException {

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

        if (!com.docusign.controller.eSignature.examples.EnvelopeHelpers.ENVELOPE_STATUS_CREATED.equalsIgnoreCase(envelope.getStatus())) {
            envelope.setStatus(com.docusign.controller.eSignature.examples.EnvelopeHelpers.ENVELOPE_STATUS_SENT);
        }

        EnvelopesApi envelopesApi = createEnvelopesApi(AppConfiguration.getBasePath(), "eyJ0eXAiOiJNVCIsImFsZyI6IlJTMjU2Iiwia2lkIjoiNjgxODVmZjEtNGU1MS00Y2U5LWFmMWMtNjg5ODEyMjAzMzE3In0.AQoAAAABAAUABwCAktlLMQ_ZSAgAgPqdrTkP2UgCAGD7x7jwO4FNlDSDKXh1m-QVAAEAAAAYAAMAAAAFAAAAHQAAAAoAAAANACQAAAA0YzFhZGIwNC0wYmM0LTRmY2YtYTIzYi0xZjc1YzM2MzE4MjYiACQAAAA0YzFhZGIwNC0wYmM0LTRmY2YtYTIzYi0xZjc1YzM2MzE4MjYSAAEAAAAGAAAAand0X2JyIwAkAAAANGMxYWRiMDQtMGJjNC00ZmNmLWEyM2ItMWY3NWMzNjMxODI2.AIKvH5EzcngF4ujEFbwcvkspwMmHvGwgyy8h8VhyxOJQBZ096MjyuUwspiPrsj45el3u7QJyTzfE6k8BiN4-F99A_jopfjFKpRXJJpXO1Dxisda1x6QGlf37Y4iHZhfKfgEO9jpEEKFTj_ZxAcSoj203pTr1WGQQ-kP9tiXpJdL5Mufe55S42e48UMDCAV4Jk0tfu1t5HHVY1QgaZGnB7_IDMtBgzA0thTJ0a4SBlfZJBBur6Yqc5WCUNirWzUyVjeLMFiV3kfPSH1TaKD8GWw7Z0oe5h4yPJpPPlCeDqeT7ZwIWSAWZvNXpaAKzTiKGF5xThCz3R8sYecsHH6vSeQ");

        EnvelopeDefinition envelopeDefinition = envelope;
        EnvelopeSummary results = envelopesApi.createEnvelope(AppConfiguration.getAPIAccountID(), envelopeDefinition);

    }
}