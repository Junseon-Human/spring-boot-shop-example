package com.keduit.shop.service;

import com.keduit.shop.dto.CartDetailDTO;
import com.keduit.shop.dto.CartItemDTO;
import com.keduit.shop.dto.CartOrderDTO;
import com.keduit.shop.dto.OrderDTO;
import com.keduit.shop.entity.Cart;
import com.keduit.shop.entity.CartItem;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.Member;
import com.keduit.shop.repository.CartItemRepository;
import com.keduit.shop.repository.CartRepository;
import com.keduit.shop.repository.ItemRepository;
import com.keduit.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDTO cartItemDTO, String email) {
        Item item = itemRepository.findById(cartItemDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        
        // 현재 로그인한 회원의 장바구니를 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // 현재 상품이 장바구니에 들어있는지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        
        // 이미 같은 상품이 장바구니에 있는 경우 수량만 더해줌
        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDTO.getCount());
            return savedCartItem.getId();
        }
        
        // 해당상품이 장바구니에 없을경우
        CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDTO.getCount());
        cartItemRepository.save(cartItem);
        return cartItem.getId();
    }

    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String email) {
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            return cartDetailDTOList;
        }

        cartDetailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());

        return cartDetailDTOList;
    }

    // 현재 로그인 한 회원과 장바구니에 상품을 저장한 회원이 같으면 통과
    public boolean validationCartItem(Long cartItemId, String email) {
        Member member = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();
        if (!StringUtils.equals(savedMember.getEmail(), member.getEmail())) {
            return false;
        }
        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updatecount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
    
    //장바구니에 있는 상품을 주문시에 주문상품은 주문이력으로 넘어가고 장바구니에서는 삭제
    public Long orderCartItem(List<CartOrderDTO> cartOrderDTOList, String email) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setItemId(cartItem.getItem().getId());
            orderDTO.setCount(cartItem.getCount());
            orderDTOList.add(orderDTO);
        }
        
        // 장바구니에 담은 상품을 주문하도록 주문 로직을 가지고있는 orderService.orders를 호출
        Long orderId = orderService.orders(orderDTOList, email);

        // 주문한 상품은 장바구니 에서 제거
        for (CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
}
