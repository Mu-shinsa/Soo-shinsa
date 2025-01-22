package com.Soo_Shinsa.model;

import com.Soo_Shinsa.constant.BrandStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import static com.Soo_Shinsa.constant.BrandStatus.APPLY;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 1, max = 50)
    private String registrationNum;

    @Column(nullable = false)
    @Length(min = 1, max = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String context;

    @Column(nullable = false)
    @Length(min = 1, max = 15)
    private BrandStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Brand(String registrationNum, String name, String context, BrandStatus status, User user) {
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
        this.status = status;
        this.user = user;
    }

    public void refuseBrand(BrandStatus status, String context) {
        this.status = status;
        this.context = context;
    }
    public void apply(BrandStatus status) {
        this.status = APPLY;
    }
    public void update(String registrationNum, String name, String context, BrandStatus status) {
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
        this.status = status;
    }
}
