package com.example.stock.dto;

import com.example.stock.entity.MainDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true) //이게 모든 요소를 안받아도 되는 어노테이션
@NoArgsConstructor
@ToString
@Data
@Setter
@Getter

public class MainDataDto {

    @JsonProperty("output")
    private Output output;

    private String stockCodes;

    @JsonIgnoreProperties(ignoreUnknown = true)// 이걸 안쪽에 한번더 안써서 계속 오류 났음 !!!
    @NoArgsConstructor
    @ToString
    @Getter
    @Setter

    public static class Output {

        @JsonProperty("bstp_kor_isnm") // 필드들 전부 이걸 써줘야 되는걸 몰랐음!!
        private String bstp_kor_isnm; //업종 한글 종목명//
         @JsonProperty("stck_prpr")
        private String stck_prpr; //주식 현재가
        @JsonProperty("prdy_vrss")
        private String prdy_vrss; //전일 대비
        @JsonProperty("prdy_vrss_sign")
        private String prdy_vrss_sign; //전일 대비 부호
        @JsonProperty("prdy_ctrt")
        private Double prdy_ctrt; //전일 대비율
        @JsonProperty("acml_vol")
        private String acml_vol; //누적 거래량
        @JsonProperty("prdy_vrss_vol_rate")
        private String prdy_vrss_vol_rate; //전일 대비 거래량 비율
        @JsonProperty("stck_oprc")
        private String stck_oprc; //주식 시가
        @JsonProperty("stck_hgpr")
        private String stck_hgpr; //주식 최고가
        @JsonProperty("stck_lwpr")
        private String stck_lwpr; //주식 하한가
        @JsonProperty("stck_sdpr")
        private String stck_sdpr; //주식 기준가
        @JsonProperty("hts_avls")
        private String hts_avls; //시가총액
        @JsonProperty("per")
        private String per;
        @JsonProperty("pbr")
        private String pbr;
        @JsonProperty("frgn_hldn_qty")
        private String frgn_hldn_qty;

        @JsonProperty("eps")
        private String eps;
        @JsonProperty("bps")
        private String bps;

        @JsonProperty("cpfn_cnnm")
        private String cpfn_cnnm; //자본금 통화명

    }

    @Builder
    public MainDataEntity toEntity() {
        return MainDataEntity.builder()
                .bstpKorIsnm(this.output.getBstp_kor_isnm()) // 업종 한글 종목명
                .stckPrpr(this.output.getStck_prpr()) // 주식 현재가
                .prdyVrss(this.output.getPrdy_vrss()) // 전일 대비
                .prdyVrssSign(this.output.getPrdy_vrss_sign()) // 전일 대비 부호
                .prdyCtrt(this.output.getPrdy_ctrt()) // 전일 대비율
                .acmlVol(this.output.getAcml_vol()) // 누적 거래량
                .prdyVrssVolRate(this.output.getPrdy_vrss_vol_rate()) // 전일 대비 거래량 비율
                .stckOprc(this.output.getStck_oprc()) // 주식 시가
                .stckHgpr(this.output.getStck_hgpr()) // 주식 최고가
                .stckLwpr(this.output.getStck_lwpr()) // 주식 하한가
                .stckSdpr(this.output.getStck_sdpr()) // 주식 기준가
                .htsAvls(this.output.getHts_avls()) // 시가총액
                .per(this.output.getPer()) // PER
                .pbr(this.output.getPbr()) // PBR
                .eps(this.output.getEps()) // EPS
                .bps(this.output.getBps()) // BPS
                .cpfnCnnm(this.output.getCpfn_cnnm()) // 자본금 통화명
                .frgnHldnQty(this.output.getFrgn_hldn_qty()) // 외국인 보유수량
                .stockCodes(this.stockCodes) // 주식 코드
                .build();
    }
}
