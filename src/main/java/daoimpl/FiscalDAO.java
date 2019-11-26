package main.java.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.dao.PoolConnection;
import org.apache.log4j.Logger;

import main.java.dao.IFiscalDAO;
import main.java.entity.Fiscal;

public class FiscalDAO implements IFiscalDAO<Fiscal> {
	
	private static FiscalDAO instance;
	private static Logger logger = Logger.getLogger(FiscalDAO.class);
	private PoolConnection poolConnection;
	
	private FiscalDAO(PoolConnection poolConnection) {
		this.poolConnection = poolConnection;
	}
	
	public static IFiscalDAO<Fiscal> getInstance(PoolConnection poolConnection) {
		if (instance == null) {
			instance = new FiscalDAO(poolConnection);
		}
		return instance;		
	}
	
	@Override
	public Long insert(Fiscal fiscal) {
		return insert(null, fiscal);
	}
	
	@Override
	public Long insert(Connection connection ,Fiscal fiscal) {

		Connection conn = (connection == null ? poolConnection.getConnection() : connection);
		try (PreparedStatement statement = conn.prepareStatement("INSERT INTO fiscal (total) values(?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setDouble(1, fiscal.getTotal());			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && connection == null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { logger.error(e);}
		    }
		}
		return null;
	}
	
	@Override
	public void update(Fiscal fiscal) {
		if (fiscal != null) {
			try(Connection connection = poolConnection.getConnection();
					PreparedStatement statement = connection.prepareStatement("UPDATE cashreg.fiscal SET fiscal=? WHERE id=?")) {
				statement.setDouble(1, fiscal.getTotal());
				statement.setLong(2, fiscal.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void delete(Fiscal fiscal) {		
		if (fiscal != null) {			
			try(Connection connection = poolConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM fiscal WHERE id = ?")) {
				statement.setLong(1, fiscal.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
}