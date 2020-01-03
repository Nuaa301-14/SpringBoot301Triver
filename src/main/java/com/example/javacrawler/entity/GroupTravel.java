package com.example.javacrawler.entity;

import java.util.Date;

/**
 * 跟团游
 */
public class GroupTravel {
    private String GroupName;

    private String GroupId;

    private float GroupScore;

    private int CommentNumber;

    private int SoldNumber;

    private String Source;

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    @Override
    public String toString() {
        return "GroupTravel{" +
                "GroupName='" + GroupName + '\'' +
                ", GroupId='" + GroupId + '\'' +
                ", GroupScore=" + GroupScore +
                ", CommentNumber=" + CommentNumber +
                ", SoldNumber=" + SoldNumber +
                ", Source='" + Source + '\'' +
                ", GroupPrice=" + GroupPrice +
                ", GroupUrl='" + GroupUrl + '\'' +
                ", Departure='" + Departure + '\'' +
                ", Time=" + Time +
                ", Introduce='" + Introduce + '\'' +
                ", Supply='" + Supply + '\'' +
                '}';
    }

    private int GroupPrice;

    private String GroupUrl;

    private String Departure;

    private String Destination;

    /**
     * 班期
     */
    private Date Time;

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

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
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

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
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

}
