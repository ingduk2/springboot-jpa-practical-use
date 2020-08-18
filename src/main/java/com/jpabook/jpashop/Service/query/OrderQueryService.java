package com.jpabook.jpashop.Service.query;


import com.jpabook.jpashop.api.OrderApiController;
import com.jpabook.jpashop.domain.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * OSIV false 면 쿼리는 서비스를 만드는방법이 있다.
 * controller의 변환로직을 가져와서 아래에서 돌리자.
 */
@Transactional(readOnly = true)
public class OrderQueryService {


//    public List<OrderApiController.OrderDto> ordersV3(){
//        List<Order> orders = orderRepository.findAllWithItem();
//        return orders.stream()
//                .map(o -> new OrderApiController.OrderDto(o))
//                .collect(toList());
//    }
}
