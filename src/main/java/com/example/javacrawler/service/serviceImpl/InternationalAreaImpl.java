package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.InternationalArea;
import com.example.javacrawler.mapper.InternationalAreaMapper;
import com.example.javacrawler.service.InternationalAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternationalAreaImpl implements InternationalAreaService {

    @Autowired
    private InternationalAreaMapper internationalAreaMapper;

    @Override
    public InternationalArea getInternationalArea(int city_id) {
        return internationalAreaMapper.getInternationalArea(city_id);
    }

    @Override
    public InternationalArea getInternationalAreaByName(String city_name) {
        return internationalAreaMapper.getInternationalAreaByName(city_name);
    }

    @Override
    public void updateInternationalArea(InternationalArea internationalArea) {
        internationalAreaMapper.updateInternationalArea(internationalArea);
    }

    @Override
    public int insertInternationalArea(InternationalArea internationalArea) {
        return internationalAreaMapper.insertInternationalArea(internationalArea);
    }

    @Override
    public int deleteInternationalArea(int city_id, String city_name) {
        return internationalAreaMapper.deleteInternationalArea(city_id, city_name);
    }
}
