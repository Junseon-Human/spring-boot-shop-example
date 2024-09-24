package com.keduit.shop.dto;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemDTO {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력입니다.")
    private Integer price;

    @NotNull(message = "재고는 필수 입력입니다.")
    private Integer stockNumber;

    @NotBlank(message = "상세설명은 필수 입력입니다.")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();
    
//  DTO -> 엔티티
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

//  엔티티 -> DTO
    public static ItemDTO of (Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }



}
