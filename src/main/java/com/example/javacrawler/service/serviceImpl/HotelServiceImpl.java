package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.mapper.HotelMapper;
import com.example.javacrawler.service.HotelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        hotelMapper.updateHotel(hotel);
    }

    @Override
    public int insertHotel(Hotel hotel) {
        return hotelMapper.insetHotel(hotel);
    }

    @Override
    public Hotel getHotel(String hotelId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("hotelId", hotelId);
        return hotelMapper.select(param);
    }

    @Override
    public Hotel getHotelByNameAndSource(String name, String source) {
        return hotelMapper.selectByNameAndSource(name,source);
    }

    @Override
    public PageInfo<Hotel> selectHotelList(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<Hotel> hotelList = hotelMapper.list(map);
        return new PageInfo<>(hotelList);
    }

    @Override
    public PageInfo<Hotel> searchHotel(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<Hotel> hotelList = hotelMapper.searchHotel(map,(String)map.get("size"));
        return new PageInfo<>(hotelList);
    }

    @Override
    public int delete(Map map) {
        return hotelMapper.delete(map);
    }

    @Override
    public PageInfo<Hotel> detailSearch(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<Hotel> hotelList=hotelMapper.detailSearch(map,(String)map.get("price_sort"));
        return new PageInfo<>(hotelList);
    }


}
