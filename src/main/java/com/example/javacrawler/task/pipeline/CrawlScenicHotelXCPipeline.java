package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.service.ScenicHotelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CrawlScenicHotelXCPipeline implements Pipeline {

    private ScenicHotelService scenicHotelService;

    public CrawlScenicHotelXCPipeline(ScenicHotelService scenicHotelService) {
        this.scenicHotelService = scenicHotelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //TODO

    }
}
