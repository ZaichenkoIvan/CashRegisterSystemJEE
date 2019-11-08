package project.model.service.impl;

import org.apache.log4j.Logger;
import project.model.dao.OrderDao;
import project.model.domain.Order;
import project.model.entity.OrderEntity;
import project.model.exception.InvalidCreationRuntimeException;
import project.model.service.OrderService;
import project.model.service.mapper.OrderMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderDao orderDao, OrderMapper mapper) {
        this.orderDao = orderDao;
        this.mapper = mapper;
    }

    @Override
    public boolean createOrder(Order Order) {
        if (Objects.isNull(Order)) {
            LOGGER.warn("Order is not valid");
            throw new InvalidCreationRuntimeException("Order is not valid");
        }

        return orderDao.save(mapper.mapOrderToOrderEntity(Order));
    }

    @Override
    public List<Order> findAll(int currentPage, int recordsPerPage) {
        List<OrderEntity> result = orderDao.findAll(currentPage, recordsPerPage);

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(mapper::mapOrderEntityToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfRows() {
        return orderDao.getNumberOfRows();
    }
}
