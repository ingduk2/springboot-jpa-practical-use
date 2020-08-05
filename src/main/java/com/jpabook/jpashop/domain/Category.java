package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    //중간테이블 필요 관계형 DB에서 풀기위해
    //실무에서는 안쓰는데.. 필드를 더 추가할수가 없다. 더많은 매핑이 필요한 경우가 많기 때문
    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items =  new ArrayList<>();

    //내 자신 self로 내 자신을 양방향 mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관관계 메서드//
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
