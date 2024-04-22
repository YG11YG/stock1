package com.example.stock.service;

import com.example.stock.entity.DataEntity;
import com.example.stock.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private DataRepository dataRepository;

    public List<DataEntity> searchByKeyword(String keyword) {
        return dataRepository.findByEnglishNameContaining(keyword);
    }

}
