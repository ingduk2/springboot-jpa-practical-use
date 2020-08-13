package com.jpabook.jpashop.Service;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepositoryOld;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        //누군가는 이렇게 할수가 있다..
        //OrderItem orderItem1 = new OrderItem();
        //orderItem1.setCount(1);
        //그래서 protected로 제약.. JPA 에서 protected까지 허용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        //원래는 딜리버리로 persist 하고, 상품도 persist 하고해야하는데 cascade 때문에 order만 한것
        //order 가 delivery 관리 , orderItem 관리 하는 경우
        //위 정도에서만 사용. 참조하는게 주인이 private 오너인 경우만 사용 cascade
        //라이프사이클 동일하게 관리할때 의미. 다른것이 참조하지않는 주인일 경우.
        //다른곳에서 관리하는 중요한 경우에는 다지워지고, 다들어가버린다.
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소 //JPA의 강점이 나옴! cancel에서
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
