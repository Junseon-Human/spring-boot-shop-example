package com.keduit.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainItemDTO {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private Integer price;

    private String imgUrl;

    // QueryProjection 은 반드시 생성자에 삽입
    // QueryDsl로 결과를 조회하면 MainItemDTO 객체로 반환됨
    @QueryProjection
    public MainItemDTO(Long id, String itemNm, String itemDetail, Integer price, String imgUrl) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.price = price;
        this.imgUrl = imgUrl;
    }


}
