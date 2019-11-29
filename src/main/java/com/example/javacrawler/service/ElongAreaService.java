package com.example.javacrawler.service;

import com.example.javacrawler.entity.ElongArea;

public interface ElongAreaService {
    ElongArea getArea(int city_id);

    ElongArea getAreaByname(String city_name);

    void updateArea(ElongArea area);

    void insertArea(ElongArea area);

    void deleteArea(int city_id,String city_name);

    ElongArea getByCity_nameOrPinyin(String City_nameOrPinyin);

    int getTotal();
}
