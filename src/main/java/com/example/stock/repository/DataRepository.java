package com.example.stock.repository;

import com.example.stock.entity.DataEntity;
import com.example.stock.entity.MainDataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {


    @Query("SELECT d.stockCodes FROM DataEntity d")
    List<String> findAllStockCodes(Pageable pageable);
    List<DataEntity> findByEnglishNameContaining(String englishName);
    @Query("SELECT d.stockCodes FROM DataEntity d")
    List<String> findAllStockCodes();
}
