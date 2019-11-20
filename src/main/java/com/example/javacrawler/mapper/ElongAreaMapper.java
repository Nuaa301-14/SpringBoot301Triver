package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.ElongArea;
import org.springframework.stereotype.Repository;

@Repository
public interface ElongAreaMapper {

    /**
     * 根据城市id获取区域
     * @param city_id
     * @return
     */
    ElongArea getElongArea(int city_id);


    /**
     * 根据城市名称获取区域
     * @param city_name
     * @return
     */
    ElongArea getElongAreaByName(String city_name);

    /**
     * 更新区域
     * @param area
     */
    void updateElongArea(ElongArea area);

    /**
     * 向数据库中出入数据
     * @param area
     * @return
     */
    int insertElongArea(ElongArea area);

    /**
     * 删除数据
     * @param city_id
     * @param city_name
     * @return
     */
    int deleteElongArea(int city_id,String city_name);

    /**
     * 搜索数据库
     * @param City_nameOrPinyin
     * @return
     */
    ElongArea getElongByCity_nameOrPinyin(String City_nameOrPinyin);

    /**
     * 更新来源
     * @param resource
     * @return
     */
    int updateAllElong(String resource);

    /**
     * 获得总数
     * @return
     */
    int getTotal();
}
