package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.SpotAndHotel;
import com.example.javacrawler.mapper.ScenicHotelMapper;
import com.example.javacrawler.service.ScenicHotelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScenicHotelServiceImpl implements ScenicHotelService {
    @Autowired
    private ScenicHotelMapper scenicHotelMapper;

    @Override
    public boolean isExist(SpotAndHotel spotAndHotel) {
        return scenicHotelMapper.getScenicHotelById(spotAndHotel.getHotelsId())!=null;
    }

    @Override
    public void updateScenicHotel(SpotAndHotel spotAndHotel) {
        scenicHotelMapper.update(spotAndHotel);
    }

    @Override
    public int insert(SpotAndHotel spotAndHotel) {
        return scenicHotelMapper.insert(spotAndHotel);
    }

    @Override
    public SpotAndHotel getScenicHotel(String produceId) {
        return scenicHotelMapper.getScenicHotelById(produceId);
    }

    @Override
    public PageInfo<SpotAndHotel> selectScenicHotelList(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        List<SpotAndHotel> spotAndHotelList = scenicHotelMapper.selectList(map);
        return new PageInfo<>(spotAndHotelList);
    }

}
