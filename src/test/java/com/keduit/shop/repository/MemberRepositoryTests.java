package com.keduit.shop.repository;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 테스트")
    public void createMemberTest() {
        MemberDTO member = new MemberDTO();
        member.setName("한정교");
        member.setEmail("tester@test.com");
        member.setPassword("1234");
        member.setAddress("서울시 동작구 보라매동");

        Member mem = Member.createMember(member, passwordEncoder);
        memberRepository.save(mem);
    }


}
