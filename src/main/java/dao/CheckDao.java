package dao;

import entity.CheckEntity;

public interface CheckDao extends CrudDao<CheckEntity> {
    Long insert(CheckEntity check);

    int update(Integer value);
}
