package com.Soo_Shinsa.category;

import com.Soo_Shinsa.BaseTimeEntity;
import com.Soo_Shinsa.brand.Brand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    public Category(Brand brand, Category parent, String name) {
        this.brand = brand;
        this.parent = parent;
        this.name = name;
    }

    // 부모 카테고리가 있다면
    public static Category rootParent() {
        return new Category();
    }

    // root 가 아니라면 true 반환하게
    public boolean isNotRoot() {
        return !this.equals(rootParent());
    }
}
