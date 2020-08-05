package com.jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//jpa생성할때 reflection proxy 써야하는데 기본생성자 있어야함.
//값 타입은 setter 제거. 생성자에서만 값 초기화
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
