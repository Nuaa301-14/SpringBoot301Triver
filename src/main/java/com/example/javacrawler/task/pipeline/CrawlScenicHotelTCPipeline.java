package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.service.ScenicHotelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CrawlScenicHotelTCPipeline implements Pipeline {

    private ScenicHotelService scenicHotelService;

    public CrawlScenicHotelTCPipeline(ScenicHotelService scenicHotelService) {
        this.scenicHotelService = scenicHotelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //TODO

    }
}
