package com.docusign.DocusignAPIHandler.src.main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionHandler {

    public static ResultSet runSQL(String SQL) throws SQLException, ClassNotFoundException {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://G://codeRepository//WeldLoggerProProject//WeldMaterialControlLog_be.accdb");
        ResultSet rs = conn.createStatement().executeQuery(SQL);
        conn.close();
        return rs;
    }
}
