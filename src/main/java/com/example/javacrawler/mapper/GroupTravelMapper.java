package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.GroupTravel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GroupTravelMapper {
    /**
     * 根据ID查找跟团游
     * @param param
     * @return
     */
    GroupTravel select(@Param("param")Map param);

    /**
     * 更新GroupTravel
     * @param grouptravel
     */
    void updateGroupTravel(GroupTravel grouptravel);

    /**
     * 插入GroupTravel
     * @param grouptravel
     * @return
     */
    int insetGroupTravel(GroupTravel grouptravel);

    List<GroupTravel> selectList(@Param("param") Map map,String order);

    List<GroupTravel> searchGroup(@Param("param") Map map, String size);

    int delete(@Param("param") Map map);
}
