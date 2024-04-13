package com.example.stock.dto;

import com.example.stock.entity.LikeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
public class LikeDto {
    private Long id;
    private Long userId;
    private Long stockId;
    private LocalDateTime likedAt;


    public static class UserData {
        private Long userId;
        private String userName;
        // ... 기타 필요한 필드와 생성자, getter, setter
    }

    public static class MainData {
        private Long stockId;
        private String stockName;
        // ... 기타 필요한 필드와 생성자, getter, setter
    }

}
