package com.keduit.shop.dto;

import com.keduit.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDTO {
    private long id;                // 상품코드

    private String itemNm;          // 상품명

    private int price;              // 상품가격

    private int stockNumber;        // 재고수량

    private String itemDetail;      // 상품 상세설명

    private ItemSellStatus sellStatus; // 상품 판매상태

    private LocalDateTime regTime;  // 등록시간

    private LocalDateTime updateTime;      // 수정시간
}
