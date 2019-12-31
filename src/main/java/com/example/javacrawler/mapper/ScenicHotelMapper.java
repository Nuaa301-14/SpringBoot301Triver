package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.SpotAndHotel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScenicHotelMapper {
    //TODO

    /**
     * 根据ProduceId获取
     * @param ProduceId
     * @return
     */
    SpotAndHotel getScenicHotelById(String ProduceId);

    /**
     * 向数据库中插入信息
     * @param spotAndHotel
     * @return
     */
    int insert(SpotAndHotel spotAndHotel);

    int update(SpotAndHotel spotAndHotel);

    List<SpotAndHotel> selectList(Map map);
}
