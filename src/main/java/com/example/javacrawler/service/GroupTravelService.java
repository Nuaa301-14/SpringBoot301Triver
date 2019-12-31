package com.example.javacrawler.service;

import com.example.javacrawler.entity.GroupTravel;

public interface GroupTravelService {
    boolean isExist(GroupTravel grouptravel);

    void updateGroupTravel(GroupTravel grouptravel);

    int insertGroupTravel(GroupTravel grouptravel);

    GroupTravel getGroupTravel(String groupId);
}
