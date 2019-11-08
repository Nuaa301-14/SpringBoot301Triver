package com.example.javacrawler.service;

import com.example.javacrawler.entity.InternationalArea;

public interface InternationalAreaService {

    InternationalArea getInternationalArea(int city_id);

    InternationalArea getInternationalAreaByName(String city_name);

    void updateInternationalArea(InternationalArea internationalArea);

    int insertInternationalArea(InternationalArea internationalArea);

    int deleteInternationalArea(int city_id, String city_name);



}
