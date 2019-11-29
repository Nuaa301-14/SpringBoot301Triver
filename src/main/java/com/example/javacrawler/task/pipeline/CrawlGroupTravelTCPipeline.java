package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.service.GroupTravelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CrawlGroupTravelTCPipeline implements Pipeline {

    private GroupTravelService groupTravelService;

    public CrawlGroupTravelTCPipeline(GroupTravelService groupTravelService) {
        this.groupTravelService = groupTravelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //TODO
    }
}
