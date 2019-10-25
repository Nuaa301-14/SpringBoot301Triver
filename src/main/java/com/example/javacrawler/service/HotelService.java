package com.example.javacrawler.service;

import com.example.javacrawler.entity.Hotel;

public interface HotelService {
    // 爬取网络数据
    boolean isExist(Hotel hotel);

    void updateHotel(Hotel hotel);

    int insertHotel(Hotel hotel);

    Hotel getHotel(String hotelId);
}
