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

        double exchangeRate = ExchangeRateFetcher.getExchangeRate();


        String stckPrprString = item.getStckPrpr();

        double stckPrpr = Double.parseDouble(stckPrprString.replaceAll("[^\\d.]", ""));

        double stckPrprInUSD = stckPrpr / exchangeRate;

        DecimalFormat df = new DecimalFormat("#.###");
        stckPrprInUSD = Double.parseDouble(df.format(stckPrprInUSD));

        item.setStckPrprInUSD(stckPrprInUSD);


        try {
            String htsAvlsHTSString = item.getHtsAvls();
            if (htsAvlsHTSString != null && !htsAvlsHTSString.isEmpty()) {
                double htsAvlsHTS = Double.parseDouble(htsAvlsHTSString.replaceAll("[^\\d.]", ""));
                double htsAvlsHTSInUSD = htsAvlsHTS / exchangeRate;
                htsAvlsHTSInUSD = Double.parseDouble(df.format(htsAvlsHTSInUSD));
                item.setHtsAvlsHTSInUSD(htsAvlsHTSInUSD);
            }
        } catch (NumberFormatException e) {
            log.error("Number Format Exception for htsAvlsHTS: " + e.getMessage());

        }

        String prdyVrssString = item.getPrdyVrss();

        double stckprdyVrss = Double.parseDouble(prdyVrssString.replaceAll("[^\\d.]", ""));

        double prdyVrssUSD = stckprdyVrss / exchangeRate;

        prdyVrssUSD = Double.parseDouble(df.format(prdyVrssUSD));

        item.setPrdyVrssUSD(prdyVrssUSD);

        return item;
    }
}