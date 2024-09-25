package com.keduit.shop.service;

import com.keduit.shop.dto.OrderDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.Member;
import com.keduit.shop.entity.Order;
import com.keduit.shop.entity.OrderItem;
import com.keduit.shop.repository.ItemRepository;
import com.keduit.shop.repository.MemberRepository;
import com.keduit.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public  Long order(OrderDTO orderDTO, String email) {
        Item item = itemRepository.findById(orderDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());

        orderItems.add(orderItem);

        Order order = Order.createOrder(member, orderItems);
        orderRepository.save(order);

        return order.getId();
    }

}
