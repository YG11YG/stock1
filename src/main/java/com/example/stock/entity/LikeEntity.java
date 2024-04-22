package com.example.stock.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@ToString
@Setter
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId")
    private MainDataEntity mainDataEntity;


    @Builder
    public LikeEntity(Long id, User user, MainDataEntity mainDataEntity) {
        this.id = id;
        this.user = user;
        this.mainDataEntity = mainDataEntity;

    }




}

