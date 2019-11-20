package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.ElongArea;
import com.example.javacrawler.mapper.ElongAreaMapper;
import com.example.javacrawler.service.ElongAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElongAreaServiceImpl implements ElongAreaService {

    @Autowired
    private ElongAreaMapper elongAreaMapper;

    @Override
    public ElongArea getArea(int city_id) {
        return elongAreaMapper.getElongArea(city_id);
    }

    @Override
    public ElongArea getAreaByname(String city_name) {
        return elongAreaMapper.getElongAreaByName(city_name);
    }

    @Override
    public void updateArea(ElongArea area) {
        elongAreaMapper.updateElongArea(area);
    }

    @Override
    public void insertArea(ElongArea area) {
        elongAreaMapper.insertElongArea(area);
    }

    @Override
    public void deleteArea(int city_id, String city_name) {
        elongAreaMapper.deleteElongArea(city_id,city_name);
    }

    @Override
    public ElongArea getByCity_nameOrPinyin(String City_nameOrPinyin) {
        return elongAreaMapper.getElongByCity_nameOrPinyin(City_nameOrPinyin);
    }

    @Override
    public int updateAll(String resource) {
        return elongAreaMapper.updateAllElong(resource);
    }

    @Override
    public int getTotal() {
        return elongAreaMapper.getTotal();
    }
}
