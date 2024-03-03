package com.example.stock.dto;

import com.example.stock.entity.AccessEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenResponse {
    @JsonProperty("access_token")
    private String access_token;

    public String getAccessToken() {
        return access_token;
    }

    public AccessEntity toEntity() {

        return new AccessEntity(null,this.getAccess_token());

    }

}