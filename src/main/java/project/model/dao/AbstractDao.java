package project.model.dao;

import org.apache.log4j.Logger;
import project.model.exception.DatabaseRuntimeException;
import project.model.dao.connector.PoolConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<E> {
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);
    
    protected PoolConnector connector;

    public AbstractDao(PoolConnector connector) {
        this.connector = connector;
    }

    protected boolean save(E entity, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            createStatementMapper(entity, preparedStatement);
            int insert =  preparedStatement.executeUpdate();
            return insert != 0;
        } catch (SQLException e) {
            LOGGER.error("Invalid entity adding" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity adding", e);
        }
    }

    protected Optional<E> findById(Integer id, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet entity = preparedStatement.executeQuery();

            return entity.next() ? mapResultSetToEntity(entity) : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Invalid entity search" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity search", e);
        }
    }

    protected List<E> findByStringParam(String data, String query) {
        List<E> result = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            ResultSet entities = statement.executeQuery();

            while(entities.next()) {
                mapResultSetToEntity(entities).ifPresent(result::add);
            }

            return result;
        } catch (SQLException e) {
            LOGGER.error("Invalid entity search by string parameter" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity search by string parameter", e);
        }
    }

    protected Optional<E> findOneByStringParam(String data, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, data);

            ResultSet user = preparedStatement.executeQuery();

            return user.next() ? mapResultSetToEntity(user) : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Invalid entity search" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity search", e);
        }
    }

    protected List<E> findEntitiesByForeignKey(Integer id, String query) {
        List<E> result = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet payment = preparedStatement.executeQuery();

            while (payment.next()) {
                mapResultSetToEntity(payment).ifPresent(result::add);
            }

            return result;
        } catch (SQLException e) {
            LOGGER.error("Invalid entities search by foreign key" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entities search by foreign key", e);
        }
    }

    protected List<E> findAll(String query) {
        List<E> result = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet entities = statement.executeQuery(query);

            while(entities.next()) {
                mapResultSetToEntity(entities).ifPresent(result::add);
            }

            return result;
        } catch (SQLException e) {
            LOGGER.error("Invalid entities search" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entities search", e);
        }
    }

    protected void update(E entity, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            updateStatementMapper(entity, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Invalid entity updating" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity updating", e);
        }
    }

    protected boolean deleteById(Integer id, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int delete = preparedStatement.executeUpdate();
            return delete != 0;
        } catch (SQLException e) {
            LOGGER.error("Invalid entity deleting" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity deleting", e);
        }
    }

    public int getNumberOfRows(String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet entity = preparedStatement.executeQuery();
            entity.last();
            return entity.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("Invalid entity search" + e.getMessage());
            throw new DatabaseRuntimeException("Invalid entity search", e);
        }
    }

    protected abstract void updateStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;
    protected abstract void createStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;
    protected abstract Optional<E> mapResultSetToEntity(ResultSet entity) throws SQLException;
}
