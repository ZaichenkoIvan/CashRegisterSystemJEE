package main.java.dao;

import main.java.entity.Fiscal;

public interface FiscalDao extends CrudDao<Fiscal> {

	Long insert(Fiscal fiscal);
}
