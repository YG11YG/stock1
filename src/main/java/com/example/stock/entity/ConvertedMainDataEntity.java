package com.example.stock.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Data
@Entity
@Getter
@Setter
@Table(name = "ConvertedMainData")
public class ConvertedMainDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String stckPrpr; // 주식 현재가

    @Transient
    private Double stckPrprInUSD; // 달러로 변환된 주식 현재가

    // 필요한 경우 추가 필드 및 메서드...
}
