package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.GroupTravelPrice;
import com.example.javacrawler.service.GroupTravelPriceService;
import com.example.javacrawler.service.GroupTravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupTravelService groupTravelService;

    @Autowired
    private GroupTravelPriceService groupTravelPriceService;

    @RequestMapping(value = {"/admin/detail"})
    public String groupDetail(
            @RequestParam(defaultValue = "", required = false) String groupId,
            Model model) {
        GroupTravel groupTravel = groupTravelService.getGroupTravel(groupId);
        List<GroupTravelPrice> groupTravelPriceList = new ArrayList<>();
        if (groupTravel != null) {
            groupTravelPriceList = groupTravelPriceService.selectList(groupTravel.getGroupId());
        }
        model.addAttribute("groupTravel", groupTravel);
        model.addAttribute("groupTravelPriceList", groupTravelPriceList);
        return "Admin/groupDetail";
    }

    @PostMapping(value = "/admin/delete")
    @ResponseBody
    public Map<String, Object> hotelDelete(
            @RequestParam("groupId") String groupId) {
        List<String> reply=new ArrayList<>();
        Map map=new HashMap();
        map.put("groupId",groupId);
        int number=groupTravelPriceService.deleteByGroupId(map);
        System.out.println(number);
        int size=groupTravelService.delete(map);
        if (size==1){
            // 删除成功
            reply.add("Success");
        }else {
            reply.add("Fail");
        }
        Map<String,Object> map1=new HashMap<>();
        map1.put("reply",reply);
        return map1;
    }


}
