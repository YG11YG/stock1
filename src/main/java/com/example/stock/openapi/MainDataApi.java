package com.example.stock.openapi;

import com.example.stock.dto.MainDataDto;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.repository.AccessRepository;
import com.example.stock.repository.DataRepository;
import com.example.stock.repository.MainDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class MainDataApi {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job mainDataProcessingJob;

    @Autowired
    private MainDataRepository mainDataRepository;
    @Autowired
    private TokenRequest tokenRequest;
    @Autowired
    private DataRepository dataRepository;
    private static final int INITIAL_BACKOFF_INTERVAL = 1000;
    private static final int MAX_BACKOFF_INTERVAL = 32000;
    private static final int MAX_RETRY_ATTEMPTS = 5;

    @Scheduled(cron = "0 0/10 9-16 * * *")
    @Bean
    public void fetchStockData() throws IOException {

        String authorization = tokenRequest.getAccessToken();

        System.out.println("전설의키 " + authorization);

        //Pageable limit = PageRequest.of(0, 100);
        List<String> stockCodes = dataRepository.findAllStockCodes();//limit);


        for (String fid_input_iscd : stockCodes) {


            int currentBackoffInterval = INITIAL_BACKOFF_INTERVAL;
            int retryCount = 0;

            while (retryCount < MAX_RETRY_ATTEMPTS) {
                try {
                    URL url = new URL("https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price?tr_id=" + "FHKST01010100" + "&fid_cond_mrkt_div_code=" + "J" + "&fid_input_iscd=" + fid_input_iscd);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");


                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.setRequestProperty("authorization", authorization);
                    connection.setRequestProperty("appKey", "PSv7KA272Ztvq5laNkJhvtCszZv1gMJyLHLz");
                    connection.setRequestProperty("appSecret", "h9YuY7ehQFHvR3cuow9wyDvTuJpemccx4lndHY+rvzonoq8T/QyvJ7kJ3aMQP7Q6/iqojTZmncUcp2W3EWR8sNfL54tCWfB5Zd4okPqNkAdsmo6iDO91l5UUo4Wj96mNcLuK5EH7QFWRPFOm5k/IlcI3OwmpxUFscV+3sy153WnprBlxGVc=");
                    connection.setRequestProperty("tr_id", "FHKST01010100");
                    connection.connect();
                    try (InputStream inputStream = connection.getInputStream();
                         InputStreamReader isr = new InputStreamReader(inputStream);
                         BufferedReader br = new BufferedReader(isr)) {
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }

                        ObjectMapper mapper = new ObjectMapper();

                        MainDataDto mainData = mapper.readValue(response.toString(), MainDataDto.class);

                        mainData.setStockCodes(fid_input_iscd);
                        MainDataEntity mainDataEntity = mainData.toEntity();

                        mainDataRepository.save(mainDataEntity);


                    }

                    break;
                } catch (IOException e) {
                    e.printStackTrace();

                    retryCount++;
                    try {

                        Thread.sleep(currentBackoffInterval);
                    } catch (InterruptedException j) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(j);
                    }

                    currentBackoffInterval = Math.min(currentBackoffInterval * 2, MAX_BACKOFF_INTERVAL);
                }


            }
        }
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(mainDataProcessingJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            // 예외 처리
            System.err.println("배치 작업 실행 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

