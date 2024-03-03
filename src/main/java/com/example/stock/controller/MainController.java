package com.example.stock.controller;
import com.example.stock.OAuth.LoginUser;
import com.example.stock.OAuth.OAuthDto.SessionUser;
import com.example.stock.entity.DataEntity;
import com.example.stock.entity.MainDataEntity;
import com.example.stock.repository.DataRepository;
import com.example.stock.repository.MainDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private MainDataRepository mainDataRepository;

    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/ddd")
    public String userForm2() {

        return "ddd";
    }






    @GetMapping("/maindata")
    public String getMainData(Model model, @LoginUser SessionUser user) {
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

        if (user != null) {
            model.addAttribute("userName", user.getName());
        } else {
            // user가 null일 때 기본값 설정 (예: "Guest")
            model.addAttribute("userName", "Guest");
        }

        return "maindata"; // HTML 파일 이름
    }

/**
    @GetMapping("/allLists")
    public String getAllLists(Model model) {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "prdyCtrt");
        List<MainDataEntity> top10PrdyCtrtDesc = mainDataRepository.findTop10ByOrderByPrdyCtrtDesc(pageRequest);
       // List<MainDataEntity> top10PrdyCtrtAsc = mainDataRepository.findTop10ByOrderByPrdyCtrtAsc();
        List<MainDataEntity> top10PerAsc = mainDataRepository.findTop10ByOrderByPerAsc();
        List<MainDataEntity> top10PbrAsc = mainDataRepository.findTop10ByOrderByPbrAsc();





      // Pageable topTen = PageRequest.of(0, 10);
      // List<MainDataEntity> topPrdyCtrtDescWithStockName = mainDataRepository.findTopByPrdyCtrtDescWithStockName();
      // model.addAttribute("topPrdyCtrtDescWithStockName", topPrdyCtrtDescWithStockName);

      //  List<MainDataEntity> mainDataList = mainDataRepository.findAllWithDetails();
       // model.addAttribute("mainDataList", mainDataList);

        model.addAttribute("top10PrdyCtrtDesc", top10PrdyCtrtDesc);
       // model.addAttribute("top10PrdyCtrtAsc", top10PrdyCtrtAsc);
        model.addAttribute("top10PerAsc", top10PerAsc);
        model.addAttribute("top10PbrAsc", top10PbrAsc);

       // for (Object[] row : top10PrdyCtrtDescWithStockName) {
       //     System.out.println("ID: " + row[0].getClass() + ", Stock Name: " + row[1]);
      //  }
        return "allListsView";
    }
**/

   /** @GetMapping("/dataList")
    public String dataList(Model model) {
        List<DataEntity> allDataWithEnglishName = dataRepository.findAllWithEnglishName();
        model.addAttribute("dataList", allDataWithEnglishName);

        System.out.println(allDataWithEnglishName.toString() + "영어이름");
        return "dataListView";
    }
**/

}
