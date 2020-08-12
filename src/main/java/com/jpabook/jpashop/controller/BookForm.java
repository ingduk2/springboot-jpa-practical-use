package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public Book toEntity(){
        Book entity = new Book();
        entity.setId(id);
        entity.setName(name);
        entity.setPrice(price);
        entity.setStockQuantity(stockQuantity);
        entity.setAuthor(author);
        entity.setIsbn(isbn);
        return entity;
    }
}
