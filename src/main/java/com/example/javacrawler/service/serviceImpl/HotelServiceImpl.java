package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.mapper.HotelMapper;
import com.example.javacrawler.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public boolean isExist(Hotel hotel) {
        Map<String, String> param = new HashMap<>();
        param.put("hotelId", hotel.getHotelId());
        Hotel hotel1 = hotelMapper.select(param);
        return hotel1 != null;
    }

    @Override
    public void updateHotel(Hotel hotel) {

    }

    @Override
    public int insetHotel(Hotel hotel) {
        return 0;
    }

    @Override
    public Hotel getHotel(String hotelId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("hotelId", hotelId);
        return hotelMapper.select(param);
    }
}
