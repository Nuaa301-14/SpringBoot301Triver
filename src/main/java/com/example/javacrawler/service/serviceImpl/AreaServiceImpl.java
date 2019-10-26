package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.mapper.AreaMapper;
import com.example.javacrawler.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area getArea(int city_id) {
        return areaMapper.getArea(city_id);
    }

    @Override
    public Area getAreaByname(String city_name) {
        return areaMapper.getAreaByName(city_name);
    }

    @Override
    public void updateArea(Area area) {
        areaMapper.updateArea(area);
    }

    @Override
    public void insertArea(Area area) {
        areaMapper.insertArea(area);
    }

    @Override
    public void deleteArea(int city_id, String city_name) {
        areaMapper.deleteArea(city_id,city_name);
    }
}
