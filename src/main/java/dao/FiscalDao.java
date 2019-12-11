package dao;

import entity.FiscalEntity;

public interface FiscalDao extends CrudDao<FiscalEntity> {

    Long insert(FiscalEntity fiscal);
}
