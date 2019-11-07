package project.model.service;

import project.model.domain.Order;

import java.util.List;

public interface OrderService {
    boolean createOrder(Order Order);

    List<Order> findAllOrders();
}

