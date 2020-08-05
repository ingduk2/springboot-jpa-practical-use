package com.jpabook.jpashop.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //아래에 의해 매핑이 된
    private List<OrderItem> orderItems = new ArrayList<>();

    //non cascade
    //persist(orderItemA)
    //persist(orderItemB)
    //persist(orderItemC)
    //persist(order)

    //cascade
    //persist(order) 하면 다 전파
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //java8 은 annotaion 없이 hibernate가 지원

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [Order, cancel]


    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static void main(String[] args) {
        Member member = new Member();
        Order order = new Order();

        //양방향 해줘야하는데 깜박할수이따.
        member.getOrders().add(order);
        order.setMember(member);
    }

}
