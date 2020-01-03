package com.example.javacrawler.service;

import com.example.javacrawler.entity.Hotel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface HotelService {
    // 爬取网络数据
    boolean isExist(Hotel hotel);

    void updateHotel(Hotel hotel);

    int insertHotel(Hotel hotel);

    Hotel getHotel(String hotelId);

    Hotel getHotelByNameAndSource(String name,String source);

    PageInfo<Hotel> selectHotelList(Map map);

    PageInfo<Hotel> searchHotel(Map map);

    int delete(Map map);
}
