package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.controller.BookForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
@NoArgsConstructor
public class Book extends Item{
    private String author;
    private String isbn;

}
