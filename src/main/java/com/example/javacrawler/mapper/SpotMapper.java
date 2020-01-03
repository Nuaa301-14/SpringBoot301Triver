package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Spot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    Spot getSpotByNameAndSource(Map<String, String> param);

    List<Spot> selectList(@Param("param")Map map);

    int delete(@Param("param") Map map);

    List<Spot> searchSpot(@Param("param") Map map, String size);

}
