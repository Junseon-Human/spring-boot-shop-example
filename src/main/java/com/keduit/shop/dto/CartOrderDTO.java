package com.keduit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Setter
@Getter
@ToString
public class CartOrderDTO {
    private Long cartItemId;
    
    // 장바구니에서 여러개의 상품을 주문하므로 CartOrderDTO 를 List로 저장
    private List<CartOrderDTO> cartOrderDTOList;
}
