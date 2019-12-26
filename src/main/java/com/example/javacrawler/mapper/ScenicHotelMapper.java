package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.SpotAndHotel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ScenicHotelMapper {
    /**
     * 根据ID查找SHotel
     * @param param
     * @return
     */
    SpotAndHotel select(@Param("param")Map param);

    /**
     * 更新SHotel
     * @param shotel
     */
    void updateSHotel(SpotAndHotel shotel);

    /**
     * 插入Spot
     * @param shotel
     * @return
     */
    int insertSHotel(SpotAndHotel shotel);
}
