package com.example.javacrawler.service;

import com.example.javacrawler.entity.Area;

public interface AreaService {


    Area getArea(int city_id);

    Area getAreaByname(String city_name);

    void updateArea(Area area);

    void insertArea(Area area);

    void deleteArea(int city_id,String city_name);
}
