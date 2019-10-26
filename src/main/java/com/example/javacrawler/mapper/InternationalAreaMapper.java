package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.InternationalArea;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalAreaMapper {

    /**
     * 根据城市id获取区域
     * @param city_id
     * @return
     */
    InternationalArea getInternationalArea(int city_id);

    /**
     * 根据城市名称获取区域
     * @param city_name
     * @return
     */
    InternationalArea getInternationalAreaByName(String city_name);

    /**
     * 更新区域
     * @param area
     */
    void updateInternationalArea(InternationalArea area);

    /**
     * 向数据库中出入数据
     * @param area
     * @return
     */
    int insertInternationalArea(InternationalArea area);

    /**
     * 删除数据
     * @param city_id
     * @param city_name
     * @return
     */
    int deleteInternationalArea(int city_id,String city_name);

}
