package com.example.javacrawler.entity;

import java.util.Date;

public class GroupTravelPrice {

    private  int id;

    private String GroupId;

    private int Price;

    private Date Time;

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "GroupTravelPrice{" +
                "GroupId='" + GroupId + '\'' +
                ", Price='" + Price + '\'' +
                ", Time=" + Time +
                '}';
    }
}
