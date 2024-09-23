package com.keduit.shop.dto;

import com.keduit.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class ItemImgDTO {

    private Long id;

    private String imgName;        // 이미지 파일명

    private String oriImgName;      // 원본 이미지 이름

    private String imgUrl;      // 이미지 조회 경로

    private String repImgYn;    // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    //model mapper를 사용하기 위해서는 entity 의 필드 이름과 DTO필드 이름을 똑같이 작성할것
    private static ItemImgDTO of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDTO.class);
    }

}
