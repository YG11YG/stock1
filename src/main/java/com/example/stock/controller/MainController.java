package com.example.stock.controller;
import com.example.stock.OAuth.LoginUser;
import com.example.stock.OAuth.OAuthDto.SessionUser;
import com.example.stock.entity.DataEntity;
import com.example.stock.entity.LikeEntity;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.entity.User;
import com.example.stock.repository.DataRepository;
import com.example.stock.repository.LikeRepository;
import com.example.stock.repository.MainDataRepository;
import com.example.stock.repository.UserRepository;
import com.example.stock.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@ToString
public class MainController {

    @Autowired
    private MainDataRepository mainDataRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;


    @GetMapping("/ddd")
    public String userForm2() {

        return "ddd";
    }


    @GetMapping("/maindata")
    public String getMainData(Model model, @LoginUser SessionUser user,  @AuthenticationPrincipal OAuth2User principal) {
        List<MainDataEntity> mainDataList = mainDataRepository.findAll();
        model.addAttribute("mainDataList", mainDataList);

        //PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "prdyCtrt");
        List<MainDataEntity> top5Rising = mainDataRepository.findTop5ByOrderByPrdyCtrtDesc();//pageRequest);
        model.addAttribute("mainDataList1", top5Rising);

        List<MainDataEntity> down5Rising = mainDataRepository.findTop5ByOrderByPrdyCtrtAsc();
        model.addAttribute("mainDataList2", down5Rising);

        List<MainDataEntity> top5per = mainDataRepository.findTop5ByOrderByPerAsc();
        model.addAttribute("mainDataList3", top5per);

        List<MainDataEntity> top5pbr = mainDataRepository.findTop5ByOrderByPbrAsc();
        model.addAttribute("mainDataList4", top5pbr);

        System.out.println("mainDataList4" + top5pbr.toString() + top5pbr + "냐옹");

        if (principal != null) {
            String userEmail = principal.getAttribute("email");
            User userFromPrincipal = userRepository.findByEmail(userEmail).orElse(null);
            if (userFromPrincipal != null) {
                List<LikeEntity> likes = likeRepository.findByUserId(userFromPrincipal.getId());
                model.addAttribute("userLikes", likes);
            }
            model.addAttribute("userName", principal.getAttribute("name"));
        }
        model.addAttribute("userName", user.getName());

        return "maindata"; // HTML 파일 이름
    }




    // 좋아요 상태 확인 요청을 처리하는 메소드
    @GetMapping("/likes/status/{stockId}")
    public ResponseEntity<?> checkLikeStatus(@PathVariable Long stockId, @AuthenticationPrincipal OAuth2User principal) {
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


    @GetMapping("/stock/{id}")
    public String showStockDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<MainDataEntity> mainData = mainDataRepository.findById(id);
        if (mainData.isPresent()) {
            model.addAttribute("stock", mainData.get());

            boolean isLiked = false;
            if (principal != null) {
                String userEmail = principal.getAttribute("email");
                User user = userRepository.findByEmail(userEmail).orElse(null);
                if (user != null) {
                    isLiked = likeRepository.findByUserIdAndMainDataEntityId(user.getId(), id).isPresent();
                }
            }
            model.addAttribute("isLiked", isLiked);

            return "stockDetails"; // 상세 페이지 HTML 파일
        } else {
            // 데이터가 없는 경우 처리
            return "redirect:/maindata";
        }
    }







    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {

        List<DataEntity> searchResults = dataRepository.findByEnglishNameContaining(keyword);

        model.addAttribute("searchResults", searchResults);

        System.out.println("결과" + searchResults);

        return "searchResult";

    }






    @PostMapping("/likes/toggle/{stockId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long stockId, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userEmail = principal.getAttribute("email");
        System.out.println("User email: " + userEmail);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email: " + userEmail));

        Optional<LikeEntity> existingLike = likeRepository.findByUserIdAndMainDataEntityId(user.getId(), stockId);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            LikeEntity newLike = new LikeEntity();  // Create a new instance here
            newLike.setUser(user);
            MainDataEntity mainDataEntity = mainDataRepository.findById(stockId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid stock ID: " + stockId));
            newLike.setMainDataEntity(mainDataEntity);
            likeRepository.save(newLike);
        }

        // 사용자의 최신 좋아요 리스트 반환
        List<LikeEntity> userLikes = likeRepository.findByUserId(user.getId());

        System.out.println("유저아이디" + userLikes.toString());

        return ResponseEntity.ok(userLikes);
    }


/**
    @GetMapping("/maindata")
    public String mainPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String userEmail = principal.getAttribute("email");
            User user = userRepository.findByEmail(userEmail).orElse(null);
            if (user != null) {
                List<LikeEntity> likes1 = likeRepository.findByUserId(user.getId());
                model.addAttribute("userLikes", likes1);
            }


        }
        // 기타 필요한 데이터 모델에 추가
        return "maindata"; // 현재 페이지 이름
    }

**/




}