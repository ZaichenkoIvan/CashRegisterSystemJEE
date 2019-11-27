package main.java.dao;

import main.java.entity.Checkspec;

import java.util.List;

public interface CheckSpecDao extends CrudDao<Checkspec> {

    void insertAll(List<Checkspec> specifications);

    List<Checkspec> findAllByCheckId(Long idCheck);
}
