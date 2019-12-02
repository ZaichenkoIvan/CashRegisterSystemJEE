package main.java.dao;

import main.java.entity.CheckEntity;

public interface CheckDao extends CrudDao<CheckEntity> {
    Long insert(CheckEntity check);

    int update(Integer value);
}
