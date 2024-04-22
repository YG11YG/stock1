package com.example.stock.service;

import com.example.stock.entity.DataEntity;
import com.example.stock.repository.DataRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
    public List<DataEntity> findByEnglishNameContaining(String keyword) {
        return dataRepository.findByEnglishNameContaining(keyword);
    }
}
