package com.example.stock.repository;


import com.example.stock.entity.DataEntity;
import com.example.stock.entity.MainDataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainDataRepository extends JpaRepository<MainDataEntity, Long> {

    List<MainDataEntity> findTop5ByOrderByPrdyCtrtDesc();

    List<MainDataEntity> findTop5ByOrderByPrdyCtrtAsc();

    List<MainDataEntity> findTop5ByOrderByPerAsc();

    List<MainDataEntity> findTop5ByOrderByPbrAsc();

}


