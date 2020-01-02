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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class JsonCope {


    public List<ElongArea> getData() throws IOException {
        ClassPathResource resource = new ClassPathResource("file/TCHotel.json");
        File file = resource.getFile();
        String jsonString = FileUtils.readFileToString(file);
//        System.out.println(jsonString);

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Set<String> strings = jsonObject.keySet();
        List<ElongArea> elongAreaList = new ArrayList<>();
        for (String s : strings) {
            if (s.equals("hotCityList")) {
                continue;
            }
            System.out.println(s);
            JSONArray jsonArray = jsonObject.getJSONArray(s);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                System.out.println(jsonArray1);
                ElongArea elongArea = new ElongArea();
                int id = 0;
                String nameEn = "";
                String nameCn = "";
                for (int j = 0; j < jsonArray1.size(); j++) {
                    String string = jsonArray1.getString(j);
                    if (j == 0) {
                        id = Integer.parseInt(string);
                    } else if (j == 1) {
                        nameCn = string;
                    } else if (j == 2) {
                        nameEn = string;
                    }
                    System.out.println(string);
                }
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
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 声明数据库view的URL
            String url = "jdbc:mysql://127.0.0.1:3306/crawler?useSSL=false&allowPublicKeyRetrieval=true";
            // 数据库用户名
            String user = "root";
            // 数据库密码
            String password = "161630126";
            // 建立数据库连接，获得连接对象conn
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "insert into tcarea (cityId,cityNameCn,cityNameEn) values(?,?,?)"; // 生成一条sql语句
            // 创建一个Statment对象
            List<ElongArea> data = new JsonCope().getData();
            for (int i = 0; i < data.size(); i++) {
                System.out.println(data.get(i).toString());
                PreparedStatement ps = conn.prepareStatement(sql);

                // 为sql语句中第一个问号赋值
                ps.setInt(1, data.get(i).getCityId());
                // 为sql语句中第二个问号赋值
                ps.setString(2, data.get(i).getCityNameCn());
                // 为sql语句中第三个问号赋值
                ps.setString(3, data.get(i).getCityNameEn());
                ps.execute();
            }
            // 关闭数据库连接对象
            conn.close();
            System.out.println("jjk插入完毕！！！");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
