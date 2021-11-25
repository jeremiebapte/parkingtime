package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;


public class DataBaseConfig {

    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException,
            SQLException {
        LOGGER.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("DB.properties");

        try {
            properties.load(inputStream);
            return DriverManager.getConnection(
                    properties.getProperty("DB_URL"),
                    properties.getProperty("DB_USER"),
                    properties.getProperty("DB_PASSWORD"));
        } catch (IOException e) {
            LOGGER.error("cannot load the file from the DB");
            e.printStackTrace();
            return null;
        }
}

    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
               LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
               LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
//TODO mettre des commentaires
