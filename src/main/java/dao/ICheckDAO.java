package main.java.dao;

import java.sql.Connection;

public interface ICheckDAO<Check> extends IDAO<Check> {

	Check findById(Long id);

	Long insert(Connection connection, Check check);

	int update(String field, Object value, String where);
}
