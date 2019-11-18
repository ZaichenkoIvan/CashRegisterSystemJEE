package com.epam.project.dao;

import com.epam.project.exceptions.DataNotFoundRuntimeException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

//TODO T-> E, Mapper to Constr
public abstract class GenericAbstractDao<T> {
    private static final Logger LOGGER = Logger.getLogger(GenericAbstractDao.class);

    private Mapper<T, PreparedStatement> mapperToDB;
    private Mapper<ResultSet, T> mapperFromDB;


    protected GenericAbstractDao() {
    }

    protected void setMapperToDB(Mapper<T, PreparedStatement> mapperToDB) {
        this.mapperToDB = mapperToDB;
    }

    protected void setMapperFromDB(Mapper<ResultSet, T> mapperFromDB) {
        this.mapperFromDB = mapperFromDB;
    }

    protected List<T> findAll(Connection connection, Class t, String SQL_getAll) throws DataNotFoundRuntimeException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll);
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
//TODO:
    protected List<T> findAllFromTo(Connection connection, Class t, Integer first, Integer offset, String SQL_getAll_base)
            throws DataNotFoundRuntimeException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_getAll_base + " limit " + first + ", " + offset + ";");
            ResultSet resultSet = ps.executeQuery();
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

    protected <V> T findBy(Connection connection, Class t, String SQL_selectByParameter, V value)
            throws DataNotFoundRuntimeException {
        T item = getItemInstance(t);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapperFromDB.map(resultSet, item);
            }
            else
                throw new DataNotFoundRuntimeException();
        } catch (SQLException sqle) {
            LOGGER.error("Dont find entity ", sqle );
            throw new DataNotFoundRuntimeException();
        }
        return item;
    }

    protected <V> List<T> findAsListBy(Connection connection, Class t, String querySelectByParameter, V value)
            throws DataNotFoundRuntimeException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(querySelectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            LOGGER.error(sqle);
            throw new DataNotFoundRuntimeException();
        }
        return items;
    }

    protected boolean addToDB(Connection connection, T item, String SQL_addNew) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_addNew);
            mapperToDB.map(item, preparedStatement);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            LOGGER.error(sqle);
            return false;
        }
    }

    protected <V> boolean updateInDB(Connection connection, T item, String SQL_update, Integer paramNum, V value) {
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update);
            mapperToDB.map(item, preparedStatement);
            addParameterToPreparedStatement(preparedStatement, paramNum, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            LOGGER.error(sqle);
            return false;
        }
        return result;
    }

    protected <V> boolean deleteFromDB(Connection connection, String SQL_delete, V value) {
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            LOGGER.error(sqle);
            return false;
        }
        return result;
    }

    public Integer calculateRowCounts(Connection connection, String tableName) throws DataNotFoundRuntimeException {
        Integer result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS ROWCOUNT FROM " + tableName + ";");
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("ROWCOUNT");
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundRuntimeException();
        }
        return result;
    }

    private T getItemInstance(Class t) {
        T item = null;
        try {
            item = (T) t.newInstance();
        } catch (InstantiationException | IllegalAccessException ie) {
            LOGGER.error(ie);
        }
        return item;
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
