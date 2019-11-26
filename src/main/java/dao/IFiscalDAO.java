package main.java.dao;

import java.sql.Connection;

public interface IFiscalDAO<Fiscal> extends IDAO<Fiscal> {

	Long insert(Connection connection, Fiscal fiscal);
}
