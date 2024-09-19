package com.keduit.shop.controller;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.entity.Member;
import com.keduit.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/memberForm";
    }

//    유효성 검사 추가 전

//    @PostMapping("/new")
//    public String memberForm(MemberDTO memberDTO,) {
//        Member member = Member.createMember(memberDTO, passwordEncoder);
//        memberService.saveMember(member);
//        return "redirect:/";
//    }


    @PostMapping("/new")
//    @Valid : MemberDTO에서 유효성검사 했음을 알림
//    BindingResult : DTO에서 넣은 메세지를 가져옴
//    유효성 검사 추가하면 코드가 달라짐
    public String memberForm(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {

//       DTO의 유효성 체크 결과를 확인 -> 에러이면 입력폼을 리턴하기
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

//        회원 가입 시 메일 중복 처리 하기
        try {
        Member member = Member.createMember(memberDTO, passwordEncoder);
        memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public  String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }


}
