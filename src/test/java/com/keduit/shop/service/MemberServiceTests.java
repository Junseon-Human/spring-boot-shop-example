package com.keduit.shop.service;

import com.keduit.shop.constant.Role;
import com.keduit.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
// SpringBootTest 어노테이션 에서 @Transactional 주면 실행이후 롤백됨
@Transactional
public class MemberServiceTests {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveMemberTest() {
        Member member = new Member();
        member.setPassword("1234");
        member.setEmail("member1@member.com");
        member.setAddress("동작구 보라매1동");
        member.setName("한정교");
        member.setRole(Role.USER);
        Member savedMember = memberService.saveMember(member);
        System.out.println(savedMember);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getAddress(), savedMember.getAddress());

        assertEquals(member.getRole(), savedMember.getRole());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

}
