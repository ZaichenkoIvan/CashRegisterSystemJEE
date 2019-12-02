package dao;

import exception.DatabaseRuntimeException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenericDao<E> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractGenericDao.class);

    protected final PoolConnection connector;

    public AbstractGenericDao(PoolConnection connector) {
        this.connector = connector;
    }

    protected Long insert(E element, String query) {
        LOGGER.info("Inserting element");
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setInsertProperties(statement, element);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                LOGGER.info("Element created. Returning it to service layer");
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Can not insert element", e);
            throw new DatabaseRuntimeException("Can not insert element", e);
        }
        return null;
    }

    protected E findByLongParam(Long data, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, data);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseToOne(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can not get element", e);
            throw new DatabaseRuntimeException("Can not get element", e);
        }
        return null;
    }

    protected E findByIntParam(int data, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, data);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseToOne(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can not get element", e);
            throw new DatabaseRuntimeException("Can not get element", e);
        }
        return null;
    }

    protected E findByStringParam(String data, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseToOne(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can not get element", e);
            throw new DatabaseRuntimeException("Can not get element", e);
        }
        return null;
    }

    protected void update(E entity, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setUpdateProperties(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can not update element", e);
            throw new DatabaseRuntimeException("Can not update element", e);
        }
    }

    protected long count(String query) {
        LOGGER.info("Getting amount of element");
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                LOGGER.info("Returning amount of entity");
                return resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            LOGGER.error("Can not get count elements", e);
            throw new DatabaseRuntimeException("Can not get count elements", e);
        }
        return 0;
    }

    protected List<E> findPaginatedList(int startIdx, int amountElements, String query) {
        ResultSet resultSet;
        List<E> list;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, startIdx);
            statement.setInt(2, amountElements);
            resultSet = statement.executeQuery();
            list = parseAll(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Can not get paganation list", e);
            throw new DatabaseRuntimeException("Can not get paganation list", e);
        }
        return list;
    }

    protected List<E> findListByLongParam(Long dataInt, String query) {
        LOGGER.info("Getting");
        ResultSet resultSet;
        List<E> list;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, dataInt);
            resultSet = statement.executeQuery();
            list = parseAll(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Can not get list element", e);
            throw new DatabaseRuntimeException("Can not get list element", e);
        }
        return list;
    }

    private List<E> parseAll(ResultSet resultSet) {
        List<E> elements = new ArrayList<>();
        try {
            while (resultSet.next()) {
                elements.add(parseToOne(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Can not parse elements", e);
            throw new DatabaseRuntimeException("Can not parse elements", e);
        }
        return elements;
    }

    protected abstract void setInsertProperties(PreparedStatement statement, E element) throws SQLException;

    protected abstract void setUpdateProperties(PreparedStatement statement, E element) throws SQLException;

    protected abstract E parseToOne(ResultSet resultSet) throws SQLException;
}