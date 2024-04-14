package com.example.stock.controller;
import com.example.stock.OAuth.LoginUser;
import com.example.stock.OAuth.OAuthDto.SessionUser;
import com.example.stock.dto.StockDetailsDto;
import com.example.stock.entity.DataEntity;
import com.example.stock.entity.LikeEntity;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.entity.User;
import com.example.stock.repository.DataRepository;
import com.example.stock.repository.LikeRepository;
import com.example.stock.repository.MainDataRepository;
import com.example.stock.repository.UserRepository;
import com.example.stock.service.*;
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
    private final MainDataService mainDataService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private StockService stockService;



    @GetMapping("/maindata")
    public String getMainData(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal OAuth2User principal) {
        model.addAttribute("mainDataList", mainDataService.findAllMainData());
        model.addAttribute("mainDataList1", mainDataService.findTop5Rising());
        model.addAttribute("mainDataList2", mainDataService.findTop5Falling());
        model.addAttribute("mainDataList3", mainDataService.findTop5Per());
        model.addAttribute("mainDataList4", mainDataService.findTop5Pbr());

        if (principal != null) {
            String userEmail = principal.getAttribute("email");
            User userFromPrincipal = userService.findByEmail(userEmail).orElse(null);
            if (userFromPrincipal != null) {
                List<LikeEntity> likes = likeService.findByUserId(userFromPrincipal.getId());
                model.addAttribute("userLikes", likes);
            }
            model.addAttribute("userName", principal.getAttribute("name"));
        }
        model.addAttribute("userName", user.getName());


        String user1 = principal.getAttribute("email");
        model.addAttribute("user1",user1);




        return "maindata";
    }




    // 좋아요 상태 확인 요청을 처리하는 메소드


    @GetMapping("/likes/status/{stockId}")
    public ResponseEntity<?> checkLikeStatus(@PathVariable Long stockId, @AuthenticationPrincipal OAuth2User principal) {
        return likeService.checkLikeStatus(stockId, principal);
    }

    @PostMapping("/likes/toggle/{stockId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long stockId, @AuthenticationPrincipal OAuth2User principal) {
        return likeService.toggleLike(stockId, principal);
    }




    @GetMapping("/stock/{id}")
    public String showStockDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        StockDetailsDto details = stockService.getStockDetails(id, principal);
        if (details != null) {
            model.addAttribute("stock", details.getMainData());
            model.addAttribute("isLiked", details.isLiked());
            return "stockDetails";
        } else {
            return "redirect:/maindata";
        }
    }







    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<DataEntity> searchResults = dataService.findByEnglishNameContaining(keyword);
        model.addAttribute("searchResults", searchResults);
        System.out.println("결과" + searchResults);
        return "searchResult";
    }












}