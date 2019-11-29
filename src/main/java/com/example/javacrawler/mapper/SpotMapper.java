package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Spot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SpotMapper {
    /**
     * 根据ID查找Spot
     * @param param
     * @return
     */
    Spot select(@Param("param")Map param);

    /**
     * 更新Spot
     * @param spot
     */
    void updateSpot(Spot spot);

    /**
     * 插入Spot
     * @param spot
     * @return
     */
    int insertSpot(Spot spot);
}
