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
    private String stckPrpr;
    @Transient
    private Double stckPrprInUSD;


}
