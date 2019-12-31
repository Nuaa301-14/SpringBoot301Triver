package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.mapper.SpotMapper;
import com.example.javacrawler.service.SpotService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public Spot getSpotByNameAndSource(String spotName,String source) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("spotName", spotName);
        param.put("spotSource",source);
        return spotMapper.getSpotByNameAndSource(param);
    }

    @Override
    public PageInfo<Spot> selectSpotList(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<Spot> spotList = spotMapper.selectList(map);
        return new PageInfo<>(spotList);
    }
}

