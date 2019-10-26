package com.example.javacrawler.task;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class MybatisPipeline implements Pipeline {

    @Autowired
    private AreaService areaService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        // 获得封装好的对象
        List<Area> areaList = resultItems.get("areaList");

        //判断数据不为空
        if (areaList != null) {
            //将数据保存到数据库中
            for (Area area :
                    areaList) {
                if (area.getHead_pinyin().equals("Z")){
                    System.out.println("Z");
                }
                if (areaService.getArea(area.getCity_id())==null){
                    // 进行插入
                    areaService.insertArea(area);
                }
            }
        }

        System.out.println("完成");
    }
}
