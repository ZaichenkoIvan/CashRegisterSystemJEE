package main.java.dao;

import java.sql.Connection;
import java.util.List;

import main.java.entity.Checkspec;

public interface ICheckSpecDAO<CheckSpec> extends IDAO<CheckSpec> {

	CheckSpec findById(Long id);

	int insertAll(List<Checkspec> specifications);

	int insertAll(Connection connection, List<Checkspec> specifications);

	List<CheckSpec> findAllByCheckId(Long idCheck);
}