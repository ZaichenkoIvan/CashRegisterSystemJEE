package project.model.dao.connector;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import project.model.exception.InvalidDatabaseConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class PoolConnector {
    private static final Logger LOGGER = Logger.getLogger(PoolConnector.class);
    private static volatile DataSource dataSource;
    private static ResourceBundle resource;

    public PoolConnector(String fileConfigName) {
        resource = ResourceBundle.getBundle(fileConfigName);
    }

    public Connection getConnection() {
        if (dataSource == null) {
            synchronized (DataSource.class) {
                if (dataSource == null) {
                    BasicDataSource bs = new BasicDataSource();
                    bs.setUrl(resource.getString("db.url"));
                    bs.setDriverClassName(resource.getString("db.driver"));
                    bs.setUsername(resource.getString("db.user"));
                    bs.setPassword(resource.getString("db.password"));
                    bs.setMinIdle(5);
                    bs.setMaxIdle(10);
                    bs.setMaxOpenPreparedStatements(100);
                    dataSource = bs;
                }
            }
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Could not get connection from database", e);
            throw new InvalidDatabaseConnectionException("Could not get connection from database ", e);
        }
    }
}



