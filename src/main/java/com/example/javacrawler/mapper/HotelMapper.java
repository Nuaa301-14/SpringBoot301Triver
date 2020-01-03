package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Hotel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HotelMapper {

    Hotel select(@Param("param")Map param);


    void updateHotel(Hotel hotel);

    int insetHotel(Hotel hotel);

    Hotel selectByNameAndSource(String name,String source);

    List<Hotel> list(@Param("param")Map map);

    List<Hotel> searchHotel(@Param("param") Map map, String size);

    int delete(@Param("param") Map map);
}
