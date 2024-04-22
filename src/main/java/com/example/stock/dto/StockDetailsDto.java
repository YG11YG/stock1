package com.example.stock.dto;

import com.example.stock.entity.MainDataEntity;

public class StockDetailsDto {
    private MainDataEntity mainData;
    private boolean isLiked;
    public StockDetailsDto(MainDataEntity mainData, boolean isLiked) {
        this.mainData = mainData;
        this.isLiked = isLiked;
    }
    public MainDataEntity getMainData() {
        return mainData;
    }
    public boolean isLiked() {
        return isLiked;
    }
}
