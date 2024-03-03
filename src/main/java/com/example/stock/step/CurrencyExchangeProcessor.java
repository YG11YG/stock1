package com.example.stock.step;

import com.example.stock.service.ExchangeRateFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.stock.entity.MainDataEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;

@RequiredArgsConstructor
public class CurrencyExchangeProcessor implements ItemProcessor<MainDataEntity, MainDataEntity> {
    private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeProcessor.class);
    private final ExchangeRateFetcher exchangeRateFetcher;

    @Override
    public MainDataEntity process(MainDataEntity item) throws Exception {
        // 환율 정보를 가져옵니다.


        // 가져온 환율 정보를 기반으로 1원당 달러 계산
        double exchangeRate = ExchangeRateFetcher.getExchangeRate();


        // MainDataEntity 객체에서 주식 가격(원화)를 문자열 형태로 가져옴
        String stckPrprString = item.getStckPrpr();
        // 주식 가격에서 숫자가 아닌 문자 제거 후 double 형으로 변환
        double stckPrpr = Double.parseDouble(stckPrprString.replaceAll("[^\\d.]", ""));
        // 주식 가격을 달러로 변환
        double stckPrprInUSD = stckPrpr / exchangeRate;
        // 소수점 세 자리까지 포맷
        DecimalFormat df = new DecimalFormat("#.###");
        stckPrprInUSD = Double.parseDouble(df.format(stckPrprInUSD));
// 변환된 주식 가격(달러)을 MainDataEntity 객체에 설정
        item.setStckPrprInUSD(stckPrprInUSD);
      //  System.out.println(item.toString());
/**
// 시가총액 변환 로직 (추가된 코드)
        String htsAvlsHTSString = item.getHtsAvlsHTS();
        double htsAvlsHTS = Double.parseDouble(htsAvlsHTSString.replaceAll("[^\\d.]", ""));
        double htsAvlsHTSInUSD = htsAvlsHTS / exchangeRate;
        htsAvlsHTSInUSD = Double.parseDouble(df.format(htsAvlsHTSInUSD));
        // 변환된 시가총액을 MainDataEntity 객체에 설정 (예: setHtsAvlsHTSInUSD 메소드를 만들어야 함)
        item.setHtsAvlsHTSInUSD(htsAvlsHTSInUSD);
**/

        try {
            String htsAvlsHTSString = item.getHtsAvlsHTS();
            if (htsAvlsHTSString != null && !htsAvlsHTSString.isEmpty()) {
                double htsAvlsHTS = Double.parseDouble(htsAvlsHTSString.replaceAll("[^\\d.]", ""));
                double htsAvlsHTSInUSD = htsAvlsHTS / exchangeRate;
                htsAvlsHTSInUSD = Double.parseDouble(df.format(htsAvlsHTSInUSD));
                item.setHtsAvlsHTSInUSD(htsAvlsHTSInUSD);
            }
        } catch (NumberFormatException e) {
            log.error("Number Format Exception for htsAvlsHTS: " + e.getMessage());
            // 여기에 필요한 예외 처리 로직 추가
        }




        return item; // 처리된 MainDataEntity 객체 반환


    }
}