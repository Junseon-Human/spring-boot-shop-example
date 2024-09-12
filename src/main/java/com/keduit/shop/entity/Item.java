package com.keduit.shop.entity;

import com.keduit.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity     // 테이블 생성하는 애
@Getter
@Setter
@ToString
public class Item {
    @Id     // pk 설정
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm;          // 상품명

    @Column(nullable = false)
    private int price;              // 상품가격

    @Column(nullable = false)
    private int stockNumber;        // 재고수량

    @Lob        // 많은 글을 수용하기 위해 사용
    @Column(nullable = false)
    private String itemDetail;      // 상품 상세설명

    @Enumerated(EnumType.STRING)        // enum이라 넣어줌
    private ItemSellStatus itemSellStatus; // 상품 판매상태

    @CreationTimestamp
    private LocalDateTime regTime;  // 등록시간
    private LocalDateTime updateTime;      // 수정시간
}