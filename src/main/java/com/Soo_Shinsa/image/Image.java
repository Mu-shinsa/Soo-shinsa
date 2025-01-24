package com.Soo_Shinsa.image;

import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.constant.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private String originName;
    private String name;
    private String path;
    private String extension;

    public Image(Long targetId, TargetType targetType, String originName, String name, String path, String extension) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.originName = originName;
        this.name = name;
        this.path = path;
        this.extension = extension;
    }

    public Image (String originName, String path) {
        String[] fileName = originName.split("\\.");
        this.originName = fileName[0];
        this.extension = fileName[1];
        this.name = UUID.randomUUID().toString();
        this.path = path;
    }
}
