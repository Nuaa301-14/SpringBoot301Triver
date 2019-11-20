package com.example.javacrawler.task.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class ELongAreaPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
//        List<InternationalArea> areaList = resultItems.get("areaList");
//        //判断数据不为空
//        if (areaList != null) {
//            //将数据保存到数据库中
//            for (InternationalArea area :
//                    areaList) {
//                if (internationalAreaService.getInternationalArea(area.getCity_id())==null){
//                    // 进行插入
//                    internationalAreaService.insertInternationalArea(area);
//                }else {
//                    internationalAreaService.updateInternationalArea(area);
//                }
//                System.out.println(area.toString());
//            }
//        }
    }
}
