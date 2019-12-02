package dao;

import entity.CheckspecEntity;

import java.util.List;

public interface CheckSpecDao extends CrudDao<CheckspecEntity> {

    void insertAll(List<CheckspecEntity> specifications);

    List<CheckspecEntity> findAllByCheckId(Long idCheck);
}
