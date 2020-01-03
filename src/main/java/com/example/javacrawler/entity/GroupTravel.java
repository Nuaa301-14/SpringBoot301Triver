package com.example.javacrawler.entity;

/**
 * 跟团游
 */
public class GroupTravel {
    private String GroupName;

    private String GroupId;

    private float GroupScore;

    private int CommentNumber;

    private int SoldNumber;

    private int GroupPrice;

    private String GroupUrl;

    private String Departure;
    /**
     * 班期
     */
    private String Time;

    private String Introduce;

    private String Supply;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public float getGroupScore() {
        return GroupScore;
    }

    public void setGroupScore(float groupScore) {
        GroupScore = groupScore;
    }

    public int getCommentNumber() {
        return CommentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        CommentNumber = commentNumber;
    }

    public int getSoldNumber() {
        return SoldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        SoldNumber = soldNumber;
    }

    public int getGroupPrice() {
        return GroupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        GroupPrice = groupPrice;
    }

    public String getGroupUrl() {
        return GroupUrl;
    }

    public void setGroupUrl(String groupUrl) {
        GroupUrl = groupUrl;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getSupply() {
        return Supply;
    }

    public void setSupply(String supply) {
        Supply = supply;
    }

    @Override
    public String toString() {
        return "GroupTravel{" +
                "GroupId='" + GroupId + '\'' +
                ", GroupName='" + GroupName + '\'' +
                ", GroupScore=" + GroupScore +
                ", CommentNumber=" + CommentNumber +
                ", SoldNumber='" + SoldNumber +
                ", GroupPrice=" + GroupPrice +
                ", GroupUrl=" + GroupUrl + '\'' +
                ", Time='" + Time + '\'' +
                ", Introduce='" + Introduce + '\'' +
                ", Supply='" + Supply + '\'' +
                '}';
    }

}
