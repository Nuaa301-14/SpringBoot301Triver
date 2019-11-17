package com.example.javacrawler.service;

import com.example.javacrawler.entity.Spot;

public interface SpotService {

    boolean isExist(Spot spot);

    void updateSpot(Spot spot);

    int insertSpot(Spot spot);

    Spot getSpot(String spotId);
}
