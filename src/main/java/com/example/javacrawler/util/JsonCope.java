package com.example.javacrawler.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.javacrawler.entity.ElongArea;
import com.example.javacrawler.service.ElongAreaService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class JsonCope {


    public List<ElongArea> getData() throws IOException {
        ClassPathResource resource=new ClassPathResource("file/elongHotel.json");
        File file=resource.getFile();
        String jsonString= FileUtils.readFileToString(file);
//        System.out.println(jsonString);

        JSONObject jsonObject=JSONObject.parseObject(jsonString);
        Set<String> strings = jsonObject.keySet();
        List<ElongArea> elongAreaList=new ArrayList<>();
        for (String s:strings){
            if (s.equals("hotCityList")){
                continue;
            }
            System.out.println(s);
            JSONArray jsonArray = jsonObject.getJSONArray(s);
            for (int i=0;i<jsonArray.size();i++){
                JSONObject row = jsonArray.getJSONObject(i);
                int id= (int) row.get("cityId");
                String nameEn= (String) row.get("cityNameEn");
                String nameCn= (String) row.get("cityNameCn");
                ElongArea elongArea=new ElongArea();
                elongArea.setCityId(id);
                elongArea.setCityNameEn(nameEn);
                elongArea.setCityNameCn(nameCn);
//                System.out.println(elongArea.toString());
                elongAreaList.add(elongArea);
            }

        }
        return elongAreaList;
    }

    public static void main(String[] args) throws IOException {
        List<ElongArea> data = new JsonCope().getData();
        for (int i=0;i<data.size();i++){
            System.out.println(data.get(i).toString());
        }
    }

}
