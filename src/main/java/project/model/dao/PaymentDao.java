package project.model.dao;

import project.model.entity.PaymentEntity;

import java.util.List;

public interface PaymentDao extends CrudRepository<Integer, PaymentEntity> {
    List<PaymentEntity> findByUser(Integer id);
}
