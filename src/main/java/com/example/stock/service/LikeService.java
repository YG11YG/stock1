package com.example.stock.service;

import com.example.stock.entity.LikeEntity;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.entity.User;
import com.example.stock.repository.LikeRepository;
import com.example.stock.repository.MainDataRepository;
import com.example.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final MainDataRepository mainDataRepository;

    public List<LikeEntity> findByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    public ResponseEntity<?> checkLikeStatus(Long stockId, OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        String userEmail = principal.getAttribute("email");
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Optional<LikeEntity> like = likeRepository.findByUserIdAndMainDataEntityId(user.getId(), stockId);
        return ResponseEntity.ok().body(like.isPresent());
    }

    public ResponseEntity<?> toggleLike(Long stockId, OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userEmail = principal.getAttribute("email");
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email: " + userEmail));

        Optional<LikeEntity> existingLike = likeRepository.findByUserIdAndMainDataEntityId(user.getId(), stockId);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            LikeEntity newLike = new LikeEntity();
            newLike.setUser(user);
            MainDataEntity mainDataEntity = mainDataRepository.findById(stockId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid stock ID: " + stockId));
            newLike.setMainDataEntity(mainDataEntity);
            likeRepository.save(newLike);
        }
        List<LikeEntity> userLikes = likeRepository.findByUserId(user.getId());
        return ResponseEntity.ok(userLikes);
    }
}
