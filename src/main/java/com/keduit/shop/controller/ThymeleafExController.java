package com.keduit.shop.controller;

import com.keduit.shop.dto.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    @GetMapping("/ex01")
    public String thymeleafEx01(Model model) {
        model.addAttribute("data", "타임리프 예제");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String thymeleafEx02(Model model) {
        ItemDTO dto = new ItemDTO();
        dto.setItemNm("타임리프 상품1");
        dto.setItemDetail("타임리프 상품1 상세설명");
        dto.setPrice(20000);
//        dto.setRegTime(LocalDateTime.now());
        model.addAttribute("itemDTO",dto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping("/ex03")
    public  String thymeleadfEx03(Model model) {
        List<ItemDTO> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            ItemDTO dto = new ItemDTO();
            dto.setItemNm("테스트 상품" + i);
            dto.setItemDetail("테스트 상품 상세설명" + i);
            dto.setPrice(25000);
//            dto.setRegTime(LocalDateTime.now());

            list.add(dto);
        }
        model.addAttribute("list", list);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String thymeleadfEx04() {
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String thymeleafEx05() {
        return "thymeleafEx/thymeleafEx05";
    }

}
