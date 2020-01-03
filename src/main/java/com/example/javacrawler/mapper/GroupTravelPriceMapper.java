package com.example.javacrawler.mapper;


import com.example.javacrawler.entity.GroupTravelPrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GroupTravelPriceMapper {


    void insert(GroupTravelPrice groupTravelPrice);

    GroupTravelPrice exist(GroupTravelPrice groupTravelPrice);

    List<GroupTravelPrice> seletListById(@Param("param") Map map);

    int deleteByGroupId(@Param("param") Map map);
}
