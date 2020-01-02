package com.example.javacrawler.service;

import com.example.javacrawler.entity.Spot;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SpotService {

    boolean isExist(Spot spot);

    void updateSpot(Spot spot);

    int insertSpot(Spot spot);

    Spot getSpot(String spotId);

    Spot getSpotByNameAndSource(String spotName,String source);

    PageInfo<Spot> selectSpotList(Map map);

    int delete(Map map);

    PageInfo<Spot> searchSpot(Map map);
}
