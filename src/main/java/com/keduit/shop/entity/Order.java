package com.keduit.shop.entity;

import com.keduit.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // all : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이
    //  orphanRemoval = true : 고아 객체 제거 모드를 true로, @OneToOne, @OneToMany애노테이션의 옵션으로 사용
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
        orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문상태

//    BaseEntity를 extends 했기때문에 사용하지 않음
//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;



}
