package com.example.stock.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class AccessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String accessKey;

    public AccessEntity(Long id, String accessKey) {
        this.id = id;
        this.accessKey = accessKey;
    }
    public String getAccessKey() {
        return this.accessKey;
    }
}
