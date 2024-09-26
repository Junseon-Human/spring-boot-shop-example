package com.keduit.shop.entity;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.exception.OutOfStockException;
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
public class Item extends BaseEntity {
    @Id     // pk 설정
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm;          // 상품명

    @Column(nullable = false)
    private Integer price;              // 상품가격

    @Column(nullable = false)
    private int stockNumber;        // 재고수량

    @Lob        // 많은 글을 수용하기 위해 사용
    @Column(nullable = false)
    private String itemDetail;      // 상품 상세설명

    @Enumerated(EnumType.STRING)        // enum이라 넣어줌
    private ItemSellStatus itemSellStatus; // 상품 판매상태

    public void updateItem(ItemDTO itemDTO) {
        this.itemNm = itemDTO.getItemNm();
        this.price = itemDTO.getPrice();
        this.stockNumber = itemDTO.getStockNumber();
        this.itemDetail = itemDTO.getItemDetail();
        this.itemSellStatus = itemDTO.getItemSellStatus();

    }

    // 1. 변경 감지 : update가 작동
    // 2. 주문 수량이 재고 수량을 넘지 않도록
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }

//    BaseEntity를 extends 했기때문에 사용하지 않음
//    @CreationTimestamp
//    private LocalDateTime regTime;  // 등록시간
//    private LocalDateTime updateTime;      // 수정시간
}