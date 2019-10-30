package ua.cashregister.model.dao;

import org.apache.log4j.Logger;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.dao.mapper.MapperFromDB;
import ua.cashregister.model.dao.mapper.MapperToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class GenericAbstractDao<T> {

    private MapperToDB<T, PreparedStatement> mapperToDB;
    private MapperFromDB<ResultSet, T> mapperFromDB;
    private DaoFactory connector = new DaoFactory();

    private static final Logger log = Logger.getLogger(GenericAbstractDao.class);

    protected GenericAbstractDao() {
    }

    protected void setMapperToDB(MapperToDB<T, PreparedStatement> mapperToDB) {
        this.mapperToDB = mapperToDB;
    }

    protected void setMapperFromDB(MapperFromDB<ResultSet, T> mapperFromDB) {
        this.mapperFromDB = mapperFromDB;
    }

    protected List<T> findAll(String SQL_getAll) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = mapperFromDB.map(resultSet);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundRuntimeException("2");
        }
        return items;
    }

    protected List<T> findAllFromTo(String SQL_getAll_base) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll_base)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = mapperFromDB.map(resultSet);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundRuntimeException("1");
        }
        return items;
    }

    protected <V> T findBy(String SQL_selectByParameter, V value)
            throws DataNotFoundRuntimeException {
        try (Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter)){
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return mapperFromDB.map(resultSet);
            else
                throw new DataNotFoundRuntimeException();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundRuntimeException();
        }
    }

    protected <V> List<T> findAsListBy(String SQL_selectByParameter, V value) {
        List<T> items = new LinkedList<>();
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter)) {
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = mapperFromDB.map(resultSet);
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
