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

    // 키워드를 포함하는 데이터 검색
    public List<DataEntity> searchByKeyword(String keyword) {
        return dataRepository.findByEnglishNameContaining(keyword);
    }

    // 추가적인 검색 관련 메소드 구현...
}
