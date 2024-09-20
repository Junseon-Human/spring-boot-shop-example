package com.keduit.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // @OneToOne : 회원 테이블과 1:1 매핑이 된다
    // @JoinColumn : 외래키 지정, name : 외래키의 이름
    // @OneToONe(fetch=FetchType.EAGER): 패치 전략 EAGER와 LAZY가 있음.
    // EAGER : 즉시로딩(@OneTOOne, @ManyToOne), LAZY : 지연로딩(@OneToMany, @ManyToMany)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
