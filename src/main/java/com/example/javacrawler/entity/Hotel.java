package com.example.javacrawler.entity;

import java.util.Date;

public class Hotel {

    private String HotelName;

    private String HotelCountry;

    private String HotelLocation;

    private String EnglishName;

    private float Comprehensive;

    private int CommentNumber;

    private int Rank;

    private String HotelId;

    private String Source;

    private String Introduce;

    private String TargetUrl;

    private int Price;

    private String area;

    private String Recommend;

    private Date updated;

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getHotelCountry() {
        return HotelCountry;
    }

    public void setHotelCountry(String hotelCountry) {
        HotelCountry = hotelCountry;
    }

    public String getHotelLocation() {
        return HotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        HotelLocation = hotelLocation;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public float getComprehensive() {
        return Comprehensive;
    }

    public void setComprehensive(float comprehensive) {
        Comprehensive = comprehensive;
    }

    public int getCommentNumber() {
        return CommentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        CommentNumber = commentNumber;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getHotelId() {
        return HotelId;
    }

    public void setHotelId(String hotelId) {
        HotelId = hotelId;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getTargetUrl() {
        return TargetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        TargetUrl = targetUrl;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getRecommend() {
        return Recommend;
    }

    public void setRecommend(String recommend) {
        Recommend = recommend;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "HotelName='" + HotelName + '\'' +
                ", HotelCountry='" + HotelCountry + '\'' +
                ", HotelLocation='" + HotelLocation + '\'' +
                ", EnglishName='" + EnglishName + '\'' +
                ", Comprehensive=" + Comprehensive +
                ", CommentNumber=" + CommentNumber +
                ", Rank=" + Rank +
                ", HotelId='" + HotelId + '\'' +
                ", Source='" + Source + '\'' +
                ", Introduce='" + Introduce + '\'' +
                ", TargetUrl='" + TargetUrl + '\'' +
                ", Price=" + Price +
                ", area='" + area + '\'' +
                ", Recommend='" + Recommend + '\'' +
                '}';
    }
}
