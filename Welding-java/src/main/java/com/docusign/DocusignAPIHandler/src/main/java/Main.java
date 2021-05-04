package com.docusign.DocusignAPIHandler.src.main.java;

import com.docusign.esign.client.ApiException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException, OAuthSystemException, ApiException {
        new AppConfiguration(args[0], Integer.parseInt(args[1]), args[2]);
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();
    }
}