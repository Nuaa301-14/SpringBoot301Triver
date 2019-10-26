package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Area;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaMapper {

    /**
     * 根据城市id获取区域
     * @param city_id
     * @return
     */
    Area getArea(int city_id);

    /**
     * 根据城市名称获取区域
     * @param city_name
     * @return
     */
    Area getAreaByName(String city_name);

    /**
     * 更新区域
     * @param area
     */
    void updateArea(Area area);

    /**
     * 向数据库中出入数据
     * @param area
     * @return
     */
    int insertArea(Area area);

    /**
     * 删除数据
     * @param city_id
     * @param city_name
     * @return
     */
    int deleteArea(int city_id,String city_name);


}
