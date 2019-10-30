package ua.cashregister.model.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import ua.cashregister.model.dao.exception.DataBaseConnectionRuntimeException;
import ua.cashregister.model.dao.exception.IncorrectPropertyRuntimeException;
import ua.cashregister.model.dao.implementation.InvoiceDaoImpl;
import ua.cashregister.model.dao.implementation.ProductDaoImpl;
import ua.cashregister.model.dao.implementation.UserDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DaoFactory {

    private BasicDataSource basicDataSource;
    private static final Logger log = Logger.getLogger(DaoFactory.class);
    private Connection connection;

    DaoFactory() {
        ResourceBundle dbConfig = ResourceBundle.getBundle("dbConfig");
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        try {
            basicDataSource.setUsername(dbConfig.getString("user"));
            basicDataSource.setPassword(dbConfig.getString("password"));
            String host = dbConfig.getString("host");
            String port = dbConfig.getString("port");
            String database = dbConfig.getString("database");
            String useUnicode = dbConfig.getString("useUnicode");
            String encoding = dbConfig.getString("encoding");
            basicDataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=" + useUnicode + "&characterEncoding=" + encoding);
            basicDataSource.setMinIdle(Integer.parseInt(dbConfig.getString("minIdle")));
            basicDataSource.setMaxIdle(Integer.parseInt(dbConfig.getString("maxIdle")));
            basicDataSource.setMaxActive(Integer.parseInt(dbConfig.getString("maxActive")));
            basicDataSource.setMaxOpenPreparedStatements(Integer.parseInt(dbConfig.getString("maxOpenPreparedStatements")));
        } catch (NullPointerException | NumberFormatException npe) {
            log.error(npe);
            throw new IncorrectPropertyRuntimeException("Incorrect db property");
        }
    }

    public Connection getConnection() {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionRuntimeException();
        }
    }

    /**
     * Connection open and closing methods
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
        }
    }

    public void open() {
        connection = getConnection();
    }

    @Deprecated
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException | NullPointerException sqle) {
            log.error(sqle);
        }
    }

    void closeConnection() {
        try {
            connection.close();
        } catch (SQLException | NullPointerException sqle) {
            log.error(sqle);
        }
    }

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public ProductDao getProductDao() {
        return new ProductDaoImpl();
    }

    public InvoiceDao getInvoiceDao() {
        return new InvoiceDaoImpl();
    }
}
