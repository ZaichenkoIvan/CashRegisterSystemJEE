package ua.cashregister.model.dao;

import org.apache.log4j.Logger;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class GenericAbstractDao<T> {

    private Mapper<T, PreparedStatement> mapperToDB;
    private Mapper<ResultSet, T> mapperFromDB;
    private DaoFactory connector = new DaoFactory();

    private static final Logger log = Logger.getLogger(GenericAbstractDao.class);

    protected GenericAbstractDao() {
    }

    protected void setMapperToDB(Mapper<T, PreparedStatement> mapperToDB) {
        this.mapperToDB = mapperToDB;
    }

    protected void setMapperFromDB(Mapper<ResultSet, T> mapperFromDB) {
        this.mapperFromDB = mapperFromDB;
    }

    protected List<T> findAll(Class t, String SQL_getAll) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundRuntimeException();
        }
        return items;
    }

    protected List<T> findAllFromTo(Class t, String SQL_getAll_base) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll_base)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundRuntimeException();
        }
        return items;
    }

    protected <V> T findBy(Class t, String SQL_selectByParameter, V value)
            throws DataNotFoundRuntimeException {
        T item = getItemInstance(t);
        try (Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter)){
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, item);
            else
                throw new DataNotFoundRuntimeException();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundRuntimeException();
        }
        return item;
    }

    protected <V> List<T> findAsListBy(Class t, String SQL_selectByParameter, V value) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter)) {
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundRuntimeException();
        }
        return items;
    }

    protected boolean addToDB(T item, String SQL_addNew) {
        boolean result;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_addNew)){
            mapperToDB.map(item, preparedStatement);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    protected <V> boolean updateInDB(T item, String SQL_update, Integer paramNum, V value) {
        boolean result;
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update)) {
            mapperToDB.map(item, preparedStatement);
            addParameterToPreparedStatement(preparedStatement, paramNum, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    protected <V> boolean deleteFromDB(String SQL_delete, V value) {
        boolean result;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete)){
            addParameterToPreparedStatement(preparedStatement, 1, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    private <V> void addParameterToPreparedStatement(PreparedStatement preparedStatement, Integer paramNum, V value)
            throws SQLException {
        if (value instanceof String)
            preparedStatement.setString(paramNum, (String) value);
        if (value instanceof Integer)
            preparedStatement.setInt(paramNum, (Integer) value);
        if (value instanceof Long)
            preparedStatement.setLong(paramNum, (Long) value);
    }
}
