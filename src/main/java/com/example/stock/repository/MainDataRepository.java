package com.example.stock.repository;


import com.example.stock.entity.DataEntity;
import com.example.stock.entity.MainDataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainDataRepository extends JpaRepository<MainDataEntity, Long> {

    List<MainDataEntity> findTop5ByOrderByPrdyCtrtDesc();//Pageable pageable);
    //List<MainDataEntity> findTop10ByOrderByPrdyCtrtDesc();

    // 전날 대비 하락률 하위 10개
    List<MainDataEntity> findTop5ByOrderByPrdyCtrtAsc();

    // PER 하위 10개
    List<MainDataEntity> findTop5ByOrderByPerAsc();

    // PBR 하위 10개
    List<MainDataEntity> findTop5ByOrderByPbrAsc();

    //@Query("SELECT m FROM MainDataEntity m JOIN m.dataEntity d WHERE d.stockName LIKE %:keyword% OR d.englishName LIKE %:keyword%")
    //List<MainDataEntity> findByStockNameOrEnglishNameContaining(String keyword);

    // DataEntity의 stockName을 기준으로 MainDataEntity 검색
    List<MainDataEntity> findByDataEntityStockNameContaining(String stockName);

    // DataEntity의 englishName을 기준으로 MainDataEntity 검색
    List<MainDataEntity> findByDataEntityEnglishNameContaining(String englishName);





}


