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
    //@Query("SELECT d.englishName FROM DataEntity d")
    //lis 에 string인지 dataentity 가 들어갈지 생각하자!
    //List<String> findAllWithEnglishName();



    List<DataEntity> findByEnglishNameContaining(String englishName);


   // @Query("SELECT d FROM DataEntity d LEFT JOIN FETCH d.mainDataEntity WHERE d.stockName LIKE %?1% OR d.englishName LIKE %?1%")
   // List<DataEntity> findByStockNameOrEnglishNameContaining(String keyword);

}
