package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.mapper.GroupTravelMapper;
import com.example.javacrawler.service.GroupTravelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupTravelServiceImpl implements GroupTravelService {
    @Autowired
    private GroupTravelMapper groupTravelMapper;

    @Override
    public boolean isExist(GroupTravel grouptravel) {
        Map<String, String> param = new HashMap<>();
        param.put("groupId", grouptravel.getGroupId());
        GroupTravel grouptravel1 = groupTravelMapper.select(param);
        return grouptravel1 != null;
    }

    @Override
    public void updateGroupTravel(GroupTravel grouptravel) {
        groupTravelMapper.updateGroupTravel(grouptravel);
    }

    @Override
    public int insertGroupTravel(GroupTravel grouptravel) {
        return groupTravelMapper.insetGroupTravel(grouptravel);
    }

    @Override
    public GroupTravel getGroupTravel(String groupId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("groupId", groupId);
        return groupTravelMapper.select(param);
    }

    @Override
    public PageInfo<GroupTravel> selectGroupList(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<GroupTravel> groupTravelList=groupTravelMapper.selectList(map,(String)map.get(("order")));
        return new PageInfo<>(groupTravelList);
    }

    @Override
    public PageInfo<GroupTravel> searchGroup(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<GroupTravel> groupTravelList=groupTravelMapper.searchGroup(map,(String)map.get("size"));
        return new PageInfo<>(groupTravelList);
    }

    @Override
    public int delete(Map map) {
        return groupTravelMapper.delete(map);
    }
}
