package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.service.HotelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrawlHotelXCPipeline implements Pipeline {

    private HotelService hotelService;

    public CrawlHotelXCPipeline(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Hotel> hotelList = resultItems.get("hotelList");
        System.out.println(hotelList.size());
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        for (Hotel hotel :
                hotelList) {
            Hotel hotel1=hotelService.getHotel(hotel.getHotelId());
            if (hotel1!=null){

//                hotel.setUpdated(new Date());
                hotelService.updateHotel(hotel);
            }else {
                hotelService.insertHotel(hotel);
            }
        }
    }
}
