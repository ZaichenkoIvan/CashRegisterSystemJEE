package main.java.dao;

import java.sql.Connection;
import java.util.List;
import main.java.entity.Goods;

public interface IGoodsDAO<T> extends IDAO<T> {

	Goods findById(Long id);

	Goods findGoods(int code);

	Goods findGoods(String name);

	List<Goods> findAll(Integer page, Integer recordsPerPage);
	long count();

	void update(Connection connection, T item);
}
