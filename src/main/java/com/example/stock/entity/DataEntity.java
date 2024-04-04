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

    @Id//이거 진짜 조심해야된다 --> 다른 어노테이션을 부를수가 있으니까
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STOCKCODE")
    private String stockCodes;

    @Column(name = "STOCKNAME", length = 512)
    private String stockName;

    @Column(name = "ENGLISHNAME", length = 512)
    private String englishName;

    //@OneToOne(mappedBy = "dataEntity")
    //private MainDataEntity mainDataEntity;

   // @OneToOne(mappedBy = "dataEntity", fetch = FetchType.EAGER)
   // private MainDataEntity mainDataEntity;


}
