package com.example.stock.service;

import com.example.stock.dto.MainDataDto;

import com.example.stock.entity.MainDataEntity;
import com.example.stock.openapi.MainDataApi;
import com.example.stock.repository.MainDataRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MainDataService {}
/**
    @Autowired
    private MainDataRepository mainDataRepository;




    public String saveMainData(MainDataDto m) {
        // 1. Dto를 Entity로 변환
        MainDataEntity mainDataEntity = mainDataDto.toEntity();
        System.out.println("이거" + mainDataDto.toString());
        // 2. Repository를 사용하여 Entity를 데이터베이스에 저장
        MainDataEntity saved = mainDataRepository.save(mainDataEntity);

        // 저장된 엔티티의 ID나 다른 식별자를 반환하거나, 성공 메시지 등을 반환할 수 있습니다.
        // 예시로 여기서는 저장된 엔티티의 ID를 문자열로 반환합니다.
        return "Saved entity with ID: " + saved.getId();
    }
}


/**
    public MainDataEntity convertDtoToEntity(MainDataDto dto) {
    MainDataEntity entity = new MainDataEntity();

    entity.setBstpKorIsnm(dto.getOutput().getBstp_kor_isnm());



    return mainDataRepository.save(entity);

}
}**/

