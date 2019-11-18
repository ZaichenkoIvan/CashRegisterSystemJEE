package com.epam.project.dao;

import com.epam.project.dao.implementation.*;
import com.epam.project.exceptions.DataBaseConnectionRuntimeException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PoolConnector {

    private static BasicDataSource basicDataSource;
    private static final Logger log = Logger.getLogger(PoolConnector.class);

    public PoolConnector() {
        if (basicDataSource == null) {
            ResourceBundle dbConfig = ResourceBundle.getBundle("dbConfig");
            basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(dbConfig.getString("url"));
            basicDataSource.setDriverClassName(dbConfig.getString("driver"));
            basicDataSource.setUsername(dbConfig.getString("user"));
            basicDataSource.setPassword(dbConfig.getString("password"));
            basicDataSource.setMinIdle(Integer.parseInt(dbConfig.getString("minIdle")));
            basicDataSource.setMaxIdle(Integer.parseInt(dbConfig.getString("maxIdle")));
            basicDataSource.setMaxActive(Integer.parseInt(dbConfig.getString("maxActive")));
            basicDataSource.setMaxOpenPreparedStatements(Integer.parseInt(dbConfig.getString("maxOpenPreparedStatements")));
        }
    }

    public Connection getConnection() throws DataBaseConnectionRuntimeException {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionRuntimeException();
        }
    }
}
