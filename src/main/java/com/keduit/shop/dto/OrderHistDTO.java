package com.keduit.shop.dto;

import com.keduit.shop.constant.OrderStatus;
import com.keduit.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderHistDTO {

    public OrderHistDTO(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long orderId;   // 주문번호

    private String orderDate;   // 주문일자

    private OrderStatus orderStatus;    // 주문 상태

    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();    // 주문내역

    public void addOrderItemDTO(OrderItemDTO orderItemDTO) {
        orderItemDTOList.add(orderItemDTO);
    }



}
