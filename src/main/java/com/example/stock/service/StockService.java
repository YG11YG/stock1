package com.example.stock.service;

import com.example.stock.dto.StockDetailsDto;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.entity.User;
import com.example.stock.repository.MainDataRepository;
import com.example.stock.repository.UserRepository;
import com.example.stock.repository.LikeRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final MainDataRepository mainDataRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public StockDetailsDto getStockDetails(Long id, OAuth2User principal) {
        Optional<MainDataEntity> mainData = mainDataRepository.findById(id);
        if (mainData.isPresent()) {
            boolean isLiked = false;
            if (principal != null) {
                String userEmail = principal.getAttribute("email");
                Optional<User> user = userRepository.findByEmail(userEmail);
                if (user.isPresent()) {
                    isLiked = likeRepository.findByUserIdAndMainDataEntityId(user.get().getId(), id).isPresent();
                }
            }
            return new StockDetailsDto(mainData.get(), isLiked);
        } else {
            return null;
        }
    }
}
