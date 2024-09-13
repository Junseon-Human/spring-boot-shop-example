package com.keduit.shop.repository;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemRepositoryTests {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품 " + i);
            item.setItemDetail("테스트 상품 상세 설명 " + i);
            item.setPrice(15000 + (1000 * i));
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(i * 100);
            item.setUpdateTime(LocalDateTime.now());
            Item saveItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setItemDetail("테스트 상품 상세 설명");
        item.setPrice(15000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(500);
        item.setUpdateTime(LocalDateTime.now());
        Item saveItem = itemRepository.save(item);
        System.out.println(saveItem);
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void selectTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품명, 상품상세정보 로 조회 테스트")
    public void findByItemNmOrItemDetailTest() {
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품 5", "테스트 상품 상세 설명 2");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("가격 기준 으로 조회")
    public void findByPriceLessThanTest() {
        List<Item> itemList = itemRepository.findByPriceLessThan(20000);
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("가격 기준조회 정렬기준 DESC")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(20000);
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 상세 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("상세 설명 3");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품조회 테스트")
    public void findByItemDetailByNativeTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("상세 설명 5");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void querydslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qitem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qitem)
                .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qitem.price.desc());

        // fetch() : 쿼리팩토리로 만들어진 쿼리문을 실행
        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2 - QuerydslPredicateExecutor")
    public void queryDslTest2() {

//        this.createItemList();

        //QItem 생성
        QItem item = QItem.item;
//  BooleanBuilder = 쿼리에 들어갈 조건을 만들어 주는 빌더
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //들어갈 내용들 변수에 지정
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        //1번 쿼리
        booleanBuilder.and(item.itemDetail.like("%" + "4" + "%"));
        //gt == ~보다 크다
        //2번 쿼리
        booleanBuilder.and(item.price.gt(price));
        System.out.println(ItemSellStatus.SELL);

        //상품 판매 상태가 "SELL" 일 때만 booleanBuilder 에 판매 상태 조건을 동적으로 추가
        //StringUtils-> thymeleaf = String을 강화하여 쓰는 객체이다
        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            //3번 쿼리
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }
// 5개씩 보도록 Pageable 생성
        Pageable pageable = PageRequest.of(1, 5);
//page 를 생성하고 booleanBuilder 의 쿼리문을 이용한 itemPage 생성
        // -> 3개의 where를 모두 만족하는 쿼리문만
        Page<Item> itemPageResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total element : " + itemPageResult.getTotalElements());
        System.out.println("total pages : " + itemPageResult.getTotalPages());

        // resultSet을 getContent()로 내용을 가져와 띄움
        List<Item> resultItemList = itemPageResult.getContent();
        for (Item i : resultItemList) {
            System.out.println("상품 : " + i);
        }
    }

}
