package com.function.card.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class DaoManager {
    private static Logger log = Logger.getLogger(DaoManager.class.getName());

    public static Connection createConnection(String dbStringConnection) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbStringConnection);
        } catch (Exception e) {
            log.severe("Impossible to create connection: " + e.getLocalizedMessage());
        }
        return connection;
    }
}
