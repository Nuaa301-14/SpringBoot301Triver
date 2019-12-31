package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.service.SpotService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class CrawlSpotTCPipeline implements Pipeline {
    private SpotService spotService;

    public CrawlSpotTCPipeline(SpotService spotService) {
        this.spotService = spotService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<Spot> spotList=resultItems.get("spotList");
        for (int i=0;i<spotList.size();i++){
            if (spotService.isExist(spotList.get(i))){
                spotService.updateSpot(spotList.get(i));
            }else {
                spotService.insertSpot(spotList.get(i));
            }
        }
    }
}
