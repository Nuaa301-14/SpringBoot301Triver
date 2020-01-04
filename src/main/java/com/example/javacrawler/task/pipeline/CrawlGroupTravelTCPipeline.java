package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.GroupTravelPrice;
import com.example.javacrawler.service.GroupTravelPriceService;
import com.example.javacrawler.service.GroupTravelService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrawlGroupTravelTCPipeline implements Pipeline {

    private GroupTravelPriceService groupTravelPriceService;

    private GroupTravelService groupTravelService;

    public CrawlGroupTravelTCPipeline(GroupTravelService groupTravelService,GroupTravelPriceService groupTravelPriceService) {
        this.groupTravelService = groupTravelService;
        this.groupTravelPriceService = groupTravelPriceService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //TODO
        List<GroupTravel> groupTravelList = resultItems.get("groupTravelList");
        System.out.println("跟团游个数:" + groupTravelList.size());
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        for (GroupTravel groupTravel :
                groupTravelList) {
            Date nowDate = new Date();
//            String dateString= s.format(nowDate);
            groupTravel.setTime(nowDate);
            groupTravelService.insertGroupTravel(groupTravel);
        }
        List<GroupTravelPrice> groupTravelPriceList = resultItems.get("groupTravelPriceList");
        System.out.println("具体价格个数：" + groupTravelPriceList.size());
        for (GroupTravelPrice groupTravelPrice :
                groupTravelPriceList) {
            if (!groupTravelPriceService.exist(groupTravelPrice)) {
                groupTravelPriceService.insertGroupPrice(groupTravelPrice);
            }
        }
    }
}
