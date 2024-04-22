package com.example.stock.config;


import com.example.stock.entity.MainDataEntity;


import com.example.stock.repository.MainDataRepository;
import com.example.stock.service.ExchangeRateFetcher;
import com.example.stock.step.CurrencyExchangeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@Slf4j
@EnableBatchProcessing
public class StockBatchConfig {


    @Autowired
    private MainDataRepository mainDataRepository;

    @Bean
    public JpaPagingItemReader<MainDataEntity> reader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<MainDataEntity>()
                .name("mainDataReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .queryString("SELECT m FROM MainDataEntity m")
                .build();
    }


    @Bean
    public ItemProcessor<MainDataEntity, MainDataEntity> processor(ExchangeRateFetcher exchangeRateFetcher) {
        return new CurrencyExchangeProcessor(exchangeRateFetcher);
    }


    @Bean
    public JpaItemWriter<MainDataEntity> writer(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<MainDataEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }



    @Bean
    public Step mainDataProcessingStep(StepBuilderFactory stepBuilderFactory, ItemReader<MainDataEntity> reader, ItemProcessor<MainDataEntity, MainDataEntity> processor, ItemWriter<MainDataEntity> writer) {
        return stepBuilderFactory.get("mainDataProcessingStep")
                .<MainDataEntity, MainDataEntity>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job mainDataProcessingJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<MainDataEntity> reader, ItemProcessor<MainDataEntity, MainDataEntity> processor, ItemWriter<MainDataEntity> writer) {
        Step step = stepBuilderFactory.get("mainDataProcessingStep")
                .<MainDataEntity, MainDataEntity>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        return jobBuilderFactory.get("mainDataProcessingJob")
                .start(step)
                .build();
    }





}







