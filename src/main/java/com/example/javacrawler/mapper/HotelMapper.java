package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Hotel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface HotelMapper {

    Hotel select(@Param("param")Map param);


    void updateHotel(Hotel hotel);

    int insetHotel(Hotel hotel);
}
