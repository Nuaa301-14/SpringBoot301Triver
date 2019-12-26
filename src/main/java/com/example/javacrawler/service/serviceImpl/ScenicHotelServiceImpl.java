package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.SpotAndHotel;
import com.example.javacrawler.mapper.ScenicHotelMapper;
import com.example.javacrawler.service.ScenicHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScenicHotelServiceImpl implements ScenicHotelService {
    @Autowired
    private ScenicHotelMapper scenicHotelMapper;


    @Override
    public boolean isExist(SpotAndHotel shotel) {
        Map<String, String> param = new HashMap<>();
        param.put("shotelId", shotel.getProductId());
        SpotAndHotel temp = scenicHotelMapper.select(param);
        return temp != null;
    }

    @Override
    public void updateSHotel(SpotAndHotel shotel) {
        scenicHotelMapper.updateSHotel(shotel);
    }

    @Override
    public int insertSHotel(SpotAndHotel shotel) {
        return scenicHotelMapper.insertSHotel(shotel);
    }

    @Override
    public SpotAndHotel getSHotel(String shotelId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("shotelId", shotelId);
        return scenicHotelMapper.select(param);
    }
}
