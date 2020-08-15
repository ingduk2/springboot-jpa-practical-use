package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryDto;
import com.jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * X To One(ManyToOne , OneToOne)
 * Order 조회
 * ORder -> Member
 * ORder -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //옵션 끈 후에 강제  lazy loading 해도 가능
        for( Order order : all){
            //getMember까지는 proxy 객체
            order.getMember().getName(); //lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderQueryDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        //2
        List<SimpleOrderQueryDto> newOrders = orders.stream()
                .map(o -> new SimpleOrderQueryDto(o))
                .collect(Collectors.toList());

        return newOrders;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderQueryDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }




}
