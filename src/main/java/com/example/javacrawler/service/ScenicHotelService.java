package com.example.javacrawler.service;

import com.example.javacrawler.entity.SpotAndHotel;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ScenicHotelService {
    //TODO

    boolean isExist(SpotAndHotel spotAndHotel);

    void updateScenicHotel(SpotAndHotel spotAndHotel);

    int insert(SpotAndHotel spotAndHotel);

    SpotAndHotel getScenicHotel(String produceId);

    PageInfo<SpotAndHotel> selectScenicHotelList(Map map);
}
