package project.model.dao;

import project.model.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends CrudDao<Integer, OrderEntity> {
    List<OrderEntity> findByInvoiceId(Integer id);
}
