package com.example.javacrawler.service;

import com.example.javacrawler.entity.SpotAndHotel;

public interface ScenicHotelService {
    boolean isExist(SpotAndHotel shotel);

    void updateSHotel(SpotAndHotel shotel);

    int insertSHotel(SpotAndHotel shotel);

    SpotAndHotel getSHotel(String shotelId);
}
