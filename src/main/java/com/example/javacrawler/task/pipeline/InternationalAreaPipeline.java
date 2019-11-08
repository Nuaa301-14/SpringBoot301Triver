package com.example.javacrawler.task.pipeline;

import com.example.javacrawler.entity.InternationalArea;
import com.example.javacrawler.service.InternationalAreaService;
import com.example.javacrawler.task.HotelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


import java.util.List;

@Component
public class InternationalAreaPipeline implements Pipeline {

    private InternationalAreaService internationalAreaService;

    public InternationalAreaPipeline(InternationalAreaService internationalAreaService) {
        this.internationalAreaService = internationalAreaService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<InternationalArea> areaList = resultItems.get("areaList");
        //判断数据不为空
        if (areaList != null) {
            //将数据保存到数据库中
            for (InternationalArea area :
                    areaList) {
                if (internationalAreaService.getInternationalArea(area.getCity_id())==null){
                    // 进行插入
                    internationalAreaService.insertInternationalArea(area);
                }else {
                    internationalAreaService.updateInternationalArea(area);
                }
                System.out.println(area.toString());
            }
        }
    }
}
