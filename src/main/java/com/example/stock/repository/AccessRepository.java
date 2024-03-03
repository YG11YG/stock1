package com.example.stock.repository;

import com.example.stock.entity.AccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<AccessEntity, Long> {

    AccessEntity findTopByOrderByIdDesc();
}
