package main.java.dao;

import main.java.entity.Check;

public interface CheckDao extends CrudDao<Check> {
    Long insert(Check check);

    int update(Integer value);
}
