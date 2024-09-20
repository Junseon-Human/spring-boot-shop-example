package com.keduit.shop.entity;

import com.keduit.shop.dto.MemberDTO;
import com.keduit.shop.repository.CartRepository;
import com.keduit.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CartTests {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    // EntityManager 의 Autowired 역할
    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setPassword("1234");
        memberDTO.setName("한정교");
        memberDTO.setEmail("han@han.com");
        memberDTO.setAddress("서울시 동장구 보라매동");
        return Member.createMember(memberDTO, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart saveCart = cartRepository.findById(cart.getId())
                .orElseThrow((EntityNotFoundException::new));
        assertEquals(saveCart.getMember().getId(), member.getId());
    }

}
