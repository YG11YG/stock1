package com.example.stock.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true) //이게 모든 요소를 안받아도 되는 어노테이션
@NoArgsConstructor
@ToString
@Data
@Table(name = "mainData")
@Entity
@Getter
@Setter
public class MainDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bstpKorIsnm; // 업종 한글 종목명

    @Column
    private String stckPrpr; // 주식 현재가

    @Column
    private String prdyVrss; // 전일 대비

    @Column
    private String prdyVrssSign; // 전일 대비 부호

    @Column
    private String prdyCtrt; // 전일 대비율

    @Column
    private String acmlVol; // 누적 거래량

    @Column
    private String prdyVrssVolRate; // 전일 대비 거래량 비율

    @Column
    private String stckOprc; // 주식 시가

    @Column
    private String stckHgpr; // 주식 최고가

    @Column
    private String stckLwpr; // 주식 하한가

    @Column
    private String stckSdpr; // 주식 기준가

    @Column
    private String htsAvlsHTS; // 시가총액

    @Column
    private String per;

    @Column
    private String pbr;

    @Column
    private String cpfnCnnm; // 자본금 통화명

    @Column(name = "STOCKCODE")
    private String stockCodes;

    @Column
    private Double stckPrprInUSD; // 달러로 변환된 주식 현재가

    @Column
    private Double HtsAvlsHTSInUSD;

    public MainDataEntity(Long id, String bstpKorIsnm, String stckPrpr, String prdyVrss, String prdyVrssSign, String prdyCtrt, String acmlVol, String prdyVrssVolRate, String stckOprc, String stckHgpr, String stckLwpr, String stckSdpr, String htsAvlsHTS, String per, String pbr, String cpfnCnnm, String stockCodes) {
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
        this.htsAvlsHTS = htsAvlsHTS;
        this.per = per;
        this.pbr = pbr;
        this.cpfnCnnm = cpfnCnnm;
        this.stockCodes = stockCodes;
    }

// eager로 바꾸니까 엔티티가 합쳐짐 문제는 아직도 null이라는 점

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //eager이 엄청 중요하네 fech관련 공부필요
    @JoinColumn(name = "id", referencedColumnName = "id")
    private DataEntity dataEntity;

    /**
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dataEntitystockCodes")
    private DataEntity dataEntities;


    public void setDataEntity(DataEntity dataEntities) {
        this.dataEntities = dataEntities;
        dataEntities.setMainDataEntity(this);
    }
**/

    /**

    @OneToMany(mappedBy = "mainDataEntity")
    private List<DataEntity> dataEntities = new ArrayList<>();

    public  void addDataEntity(DataEntity dataEntity){
        this.dataEntities.add(dataEntity);
        dataEntity.setMainDataEntity(this);
    }
**/

}


