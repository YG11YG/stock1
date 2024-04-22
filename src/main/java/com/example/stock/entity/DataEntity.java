package com.example.stock.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BASIC")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STOCKCODE")
    private String stockCodes;
    @Column(name = "STOCKNAME", length = 512)
    private String stockName;
    @Column(name = "ENGLISHNAME", length = 512)
    private String englishName;


}
