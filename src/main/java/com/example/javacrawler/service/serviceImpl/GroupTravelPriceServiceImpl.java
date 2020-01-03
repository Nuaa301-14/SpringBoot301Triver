package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.GroupTravelPrice;
import com.example.javacrawler.mapper.GroupTravelPriceMapper;
import com.example.javacrawler.service.GroupTravelPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupTravelPriceServiceImpl implements GroupTravelPriceService {

    @Autowired
    private GroupTravelPriceMapper groupTravelPriceMapper;

    @Override
    public void insertGroupPrice(GroupTravelPrice groupTravelPrice) {
        groupTravelPriceMapper.insert(groupTravelPrice);
    }

    @Override
    public boolean exist(GroupTravelPrice groupTravelPrice) {
        if (groupTravelPriceMapper.exist(groupTravelPrice)!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<GroupTravelPrice> selectList(String groupId) {
        Map map=new HashMap();
        map.put("groupId",groupId);
        map.put("date",new Date());
        return groupTravelPriceMapper.seletListById(map);
    }

    @Override
    public int deleteByGroupId(Map map) {
        return groupTravelPriceMapper.deleteByGroupId(map);
    }
}
