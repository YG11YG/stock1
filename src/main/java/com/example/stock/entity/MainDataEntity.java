package com.example.stock.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@ToString
@Data
@Table(name = "mainData")
@Entity
@Getter

public class MainDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bstpKorIsnm;

    @Column
    private String stckPrpr;

    @Column
    private String prdyVrss;

    @Column
    private String prdyVrssSign;

    @Column
    private Double prdyCtrt;

    @Column
    private String acmlVol;

    @Column
    private String prdyVrssVolRate;

    @Column
    private String stckOprc;

    @Column
    private String stckHgpr;

    @Column
    private String stckLwpr;

    @Column
    private String stckSdpr;

    @Column
    private String htsAvls;

    @Column
    private String per;

    @Column
    private String pbr;

    @Column
    private String cpfnCnnm;

    @Column(name = "STOCKCODE")
    private String stockCodes;

    @Column
    private Double stckPrprInUSD;

    @Column
    private Double HtsAvlsHTSInUSD;
    
    @Column
    private Double prdyVrssUSD;
    

    @Column
    private String frgnHldnQty;


    @Column
    private String eps;

    @Column
    private String bps;

    @Builder
    public MainDataEntity(Long id, String bstpKorIsnm, String stckPrpr, String prdyVrss, String prdyVrssSign, Double prdyCtrt, String acmlVol, String prdyVrssVolRate, String stckOprc, String stckHgpr, String stckLwpr, String stckSdpr, String htsAvls, String per, String pbr,String frgnHldnQty, String eps, String bps, String cpfnCnnm, String stockCodes) {
        this.id = id;
        this.bstpKorIsnm = bstpKorIsnm;
        this.stckPrpr = stckPrpr;
        this.prdyVrss = prdyVrss;
        this.prdyVrssSign = prdyVrssSign;
        this.prdyCtrt = prdyCtrt;
        this.acmlVol = acmlVol;
        this.prdyVrssVolRate = prdyVrssVolRate;
        this.stckOprc = stckOprc;
        this.stckHgpr = stckHgpr;
        this.stckLwpr = stckLwpr;
        this.stckSdpr = stckSdpr;
        this.htsAvls = htsAvls;
        this.per = per;
        this.pbr = pbr;
        this.cpfnCnnm = cpfnCnnm;
        this.stockCodes = stockCodes;
        this.frgnHldnQty = frgnHldnQty;
        this.eps = eps;
        this.bps = bps;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //eager이 엄청 중요하네 fech관련 공부필요
    @JoinColumn(name = "id", referencedColumnName = "id")
    private DataEntity dataEntity;

}