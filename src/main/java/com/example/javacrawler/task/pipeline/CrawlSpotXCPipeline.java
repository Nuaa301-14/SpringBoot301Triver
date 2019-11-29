package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.service.SpotService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CrawlSpotXCPipeline implements Pipeline {

    private SpotService spotService;

    public CrawlSpotXCPipeline(SpotService spotService) {
        this.spotService = spotService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //保存景点数据
        //TODO
    }
}
