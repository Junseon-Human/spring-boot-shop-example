package com.keduit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderDTO {

    @NotNull(message = "주문할 상품을 선택하세요")
    private Long itemId;

    @Min(value = 1, message = "최소 주문수량은 1개 이상 입니다.")
    @Max(value = 999, message = "최대 주문 수량은 999개 입니다.")
    private int count;
}
