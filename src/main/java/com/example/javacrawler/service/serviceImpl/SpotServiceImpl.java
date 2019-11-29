package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.mapper.SpotMapper;
import com.example.javacrawler.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private SpotMapper spotMapper;

    @Override
    public boolean isExist(Spot spot) {
        Map<String, String> param = new HashMap<>();
        param.put("spotId", spot.getSpotId());
        Spot temp = spotMapper.select(param);
        return temp != null;
    }

    @Override
    public void updateSpot(Spot spot) {
        spotMapper.updateSpot(spot);
    }

    @Override
    public int insertSpot(Spot spot) {
        return spotMapper.insertSpot(spot);
    }

    @Override
    public Spot getSpot(String spotId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("spotId", spotId);
        return spotMapper.select(param);
    }
}

