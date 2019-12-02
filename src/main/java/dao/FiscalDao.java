package main.java.dao;

import main.java.entity.FiscalEntity;

public interface FiscalDao extends CrudDao<FiscalEntity> {

	Long insert(FiscalEntity fiscal);
}
