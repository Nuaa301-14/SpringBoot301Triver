package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class HotelControl {
    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "/hello")
    public String hello(){
        Hotel hotel = hotelService.getHotel("123456");
        System.out.println(hotel.getComprehensive());
        System.out.println(hotel.getHotelLocation());
        System.out.println("springboot");
        return "Hello";
    }

}
