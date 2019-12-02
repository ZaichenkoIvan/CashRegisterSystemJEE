package dao;

import exception.DatabaseRuntimeException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PoolConnection {

    private static final Logger LOGGER = Logger.getLogger(PoolConnection.class);

    private DataSource dataSource;
    private static BasicDataSource basicDataSource;

    public PoolConnection() {
        if (basicDataSource == null) {
            try {
                ResourceBundle dbConfig = ResourceBundle.getBundle("database");
                basicDataSource = new BasicDataSource();
                basicDataSource.setUrl(dbConfig.getString("url"));
                basicDataSource.setDriverClassName(dbConfig.getString("driver"));
                basicDataSource.setUsername(dbConfig.getString("user"));
                basicDataSource.setPassword(dbConfig.getString("password"));
                basicDataSource.setMinIdle(Integer.parseInt(dbConfig.getString("minIdle")));
                basicDataSource.setMaxIdle(Integer.parseInt(dbConfig.getString("maxIdle")));
                basicDataSource.setMaxActive(Integer.parseInt(dbConfig.getString("maxActive")));
                basicDataSource.setMaxOpenPreparedStatements(Integer.parseInt(dbConfig.getString("maxOpenPreparedStatements")));
                dataSource = basicDataSource;
            } catch (Exception e) {
                LOGGER.warn("Can't create database connection", e);
            }
        }
    }

    public Connection getConnection(){
        LOGGER.info("Getting connection");
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cant get database connection", e);
            throw new DatabaseRuntimeException("Cant get database connection");
        }
    }
}