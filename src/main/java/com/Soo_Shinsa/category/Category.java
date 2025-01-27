package com.Soo_Shinsa.category;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.constant.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_Id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Builder
    public Category(Brand brand, Category parent, String name) {
        this.brand = brand;
        this.parent = parent;
        this.name = name;
    }

    public void addChild(Category child) {
        this.children.add(child);
    }

    public static List<Long> getChildByParentId(Category category) {

        if (category.getChildren() == null || category.getChildren().isEmpty()) {
            return List.of(category.getId());
        }
        return category.getChildren()
                .stream()
                .map(Category::getChildByParentId)
                .flatMap(List::stream)
                .collect(toList());
    }
}
