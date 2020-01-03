package com.example.javacrawler.service;

import com.example.javacrawler.entity.GroupTravel;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface GroupTravelService {
    boolean isExist(GroupTravel grouptravel);

    void updateGroupTravel(GroupTravel grouptravel);

    int insertGroupTravel(GroupTravel grouptravel);

    GroupTravel getGroupTravel(String groupId);

    PageInfo<GroupTravel> selectGroupList(Map map);

    PageInfo<GroupTravel> searchGroup(Map map);

    int delete(Map map);
}
