package com.example.javacrawler.service;

import com.example.javacrawler.entity.GroupTravelPrice;

import java.util.List;
import java.util.Map;

public interface GroupTravelPriceService {

    void insertGroupPrice(GroupTravelPrice groupTravelPrice);

    boolean exist(GroupTravelPrice groupTravelPrice);

    List<GroupTravelPrice> selectList(String groupId);

    int deleteByGroupId(Map map);
}
