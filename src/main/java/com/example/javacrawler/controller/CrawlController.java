package com.example.javacrawler.controller;

import com.example.javacrawler.entity.ElongArea;
import com.example.javacrawler.service.ElongAreaService;
import com.example.javacrawler.util.JsonCope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/crawl")
public class CrawlController {

    @Autowired
    private ElongAreaService elongAreaService;

    @RequestMapping(value = "/elongArea")
    public String just() throws IOException {
//        new CrawlElongArea().crawl();
        if (elongAreaService.getTotal()==0){
            List<ElongArea> data = new JsonCope().getData();
            for (int i=0;i<data.size();i++){
                ElongArea elongArea = data.get(i);
                elongAreaService.insertArea(elongArea);
            }
        }
        System.out.println(elongAreaService.getTotal());
        System.out.println("success");
        return "index";
    }



}
