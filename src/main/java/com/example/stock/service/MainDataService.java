package com.example.stock.service;

import com.example.stock.entity.MainDataEntity;
import com.example.stock.repository.MainDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainDataService {
    private final MainDataRepository mainDataRepository;

    public List<MainDataEntity> findAllMainData() {
        return mainDataRepository.findAll();
    }

    public List<MainDataEntity> findTop5Rising() {
        return mainDataRepository.findTop5ByOrderByPrdyCtrtDesc();
    }

    public List<MainDataEntity> findTop5Falling() {
        return mainDataRepository.findTop5ByOrderByPrdyCtrtAsc();
    }

    public List<MainDataEntity> findTop5Per() {
        return mainDataRepository.findTop5ByOrderByPerAsc();
    }

    public List<MainDataEntity> findTop5Pbr() {
        return mainDataRepository.findTop5ByOrderByPbrAsc();
    }
}
