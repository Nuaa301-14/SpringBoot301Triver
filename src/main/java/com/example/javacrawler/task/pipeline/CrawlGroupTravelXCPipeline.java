package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.mapper.GroupTravelMapper;
import com.example.javacrawler.service.GroupTravelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class CrawlGroupTravelXCPipeline implements Pipeline {
    private GroupTravelService groupTravelService;

    public CrawlGroupTravelXCPipeline(GroupTravelService groupTravelService) {
        this.groupTravelService = groupTravelService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<GroupTravel> groupTravelList=resultItems.get("groupTravelList");
        for (GroupTravel groupTravel :
                groupTravelList) {
            groupTravelService.insertGroupTravel(groupTravel);
        }

    }
}
