package project.model.dao.connector;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import project.model.exception.InvalidDatabaseConnectionException;


import java.sql.*;
import java.util.ResourceBundle;

public final class PoolConnector {
    private static final Logger LOGGER = Logger.getLogger(PoolConnector.class);

    private static final BasicDataSource ds = new BasicDataSource();

    public PoolConnector(String fileConfigName) {
        ResourceBundle resource = ResourceBundle.getBundle(fileConfigName);

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(resource.getString("db.url"));
        ds.setUsername(resource.getString("db.user"));
        ds.setPassword(resource.getString("db.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Could not get connection from database" + e.getMessage());
            throw new InvalidDatabaseConnectionException("Could not get connection from database " + e.getMessage());
        }
    }
}
