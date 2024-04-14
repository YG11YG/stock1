package com.example.stock.repository;

import com.example.stock.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {


    List<LikeEntity> findByUserId(Long userId);
    List<LikeEntity> findByMainDataEntityId(Long stockId);

    Optional<LikeEntity> findByUserIdAndMainDataEntityId(Long userId, Long stockId);


}
