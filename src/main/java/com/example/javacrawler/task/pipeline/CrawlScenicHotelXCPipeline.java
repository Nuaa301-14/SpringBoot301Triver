package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.SpotAndHotel;
import com.example.javacrawler.service.ScenicHotelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class CrawlScenicHotelXCPipeline implements Pipeline {

    private ScenicHotelService scenicHotelService;

    public CrawlScenicHotelXCPipeline(ScenicHotelService scenicHotelService) {
        this.scenicHotelService = scenicHotelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //TODO
        List<SpotAndHotel> spotAndHotelList = resultItems.get("spotAndHotelList");
        for (int i = 0; i <= spotAndHotelList.size(); i++) {
            if (scenicHotelService.isExist(spotAndHotelList.get(i))){
                scenicHotelService.updateScenicHotel(spotAndHotelList.get(i));
            }else {
                scenicHotelService.insert(spotAndHotelList.get(i));
            }
        }
    }
}
