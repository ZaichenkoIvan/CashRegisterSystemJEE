package project.model.service.mapper;

import project.model.domain.Invoice;
import project.model.domain.Order;
import project.model.domain.Product;
import project.model.entity.InvoiceEntity;
import project.model.entity.OrderEntity;
import project.model.entity.ProductEntity;

public class OrderMapper {
    public OrderEntity mapOrderToOrderEntity(Order domain) {
        return OrderEntity.builder()
                .withId(domain.getId())
                .withNumber(domain.getNumber())
                .withInvoiceEntity(InvoiceEntity.builder()
                        .withId(domain.getInvoice().getId())
                        .build())
                .withProductEntity(ProductEntity.builder()
                        .withId(domain.getProduct().getId())
                        .build())
                .build();
    }

    public Order mapOrderEntityToOrder(OrderEntity entity) {
        return Order.builder()
                .withId(entity.getId())
                .withNumber(entity.getNumber())
                .withInvoice(Invoice.builder()
                        .withId(entity.getInvoiceEntity().getId())
                        .build())
                .withProduct(Product.builder()
                        .withId(entity.getProductEntity().getId())
                        .build())
                .build();
    }
}
