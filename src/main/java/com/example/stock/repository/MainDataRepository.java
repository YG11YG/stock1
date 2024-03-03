package com.example.stock.repository;


import com.example.stock.entity.MainDataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

   // @Query("SELECT m FROM MainDataEntity m LEFT JOIN FETCH m.dataEntities")
  //  List<MainDataEntity> findAllWithEnglishName();

    //@Query("SELECT m FROM MainDataEntity m JOIN m.dataEntities d ORDER BY m.prdyCtrt DESC")
   // List<MainDataEntity> findTop10ByOrderByPrdyCtrtDescWithDetails(Pageable pageable);
    //@Query("SELECT m, d.englishName FROM MainDataEntity m JOIN m.dataEntities d ORDER BY m.prdyCtrt DESC")
   // List<Object[]> findTop10ByPrdyCtrtDescWithEnglishName();

   // @Query("SELECT m, d.englishName FROM MainDataEntity m LEFT JOIN m.dataEntities d ORDER BY m.prdyCtrt DESC")
    //List<Object[]> findTop10ByPrdyCtrtDescWithEnglishName();

    //@Query("SELECT m, d.stockName FROM MainDataEntity m LEFT JOIN m.dataEntities d ORDER BY m.prdyCtrt DESC")
   // List<MainDataEntity> findTopByPrdyCtrtDescWithStockName();

    //@Query("SELECT m, d.stockName FROM MainDataEntity m JOIN DataEntity d ON m.stockCode = d.stockCode ORDER BY m.prdyCtrt DESC")
    //List<Object[]> findTop10ByPrdyCtrtDescWithStockName();

    //@Query("SELECT m FROM MainDataEntity m JOIN FETCH m.dataEntities")
   // List<MainDataEntity> findAllWithDetails();
    //@Query("SELECT m FROM MainDataEntity m JOIN FETCH m.dataEntities")
   // List<MainDataEntity> findAllOrderByPrdyCtrtDesc();

  // @Query("SELECT m, d FROM MainDataEntity m LEFT JOIN FETCH m.dataEntities d")
 //  List<Object[]> findAllMainDataWithDetails();

   // @Query("SELECT m, d FROM MainDataEntity m LEFT JOIN FETCH m.dataEntities d")
   // List<MainDataEntity> findAllWithDetails();

   // @Query("SELECT m, d.englishName FROM MainDataEntity m LEFT JOIN m.dataEntities d ORDER BY m.prdyCtrt DESC")
    //List<Object[]> findTopByPrdyCtrtDescWithDetails();

}


