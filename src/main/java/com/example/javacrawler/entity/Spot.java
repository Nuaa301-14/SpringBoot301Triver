package com.example.javacrawler.entity;

public class Spot {
    private String SpotName;

    private String SpotId;

    private String SpotCountry;

    private String SpotLocation;

    private String Recommend;

    private String Source;

    private String SpotUrl;

    private String SpotArea;

    private float SpotScore;

    private int SoldNumber;

    private int SpotPrice;

    private String Introduce;

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getSpotName() {
        return SpotName;
    }

    public void setSpotName(String spotName) {
        SpotName = spotName;
    }

    public String getSpotCountry() {
        return SpotCountry;
    }

    public void setSpotCountry(String spotCountry) {
        SpotCountry = spotCountry;
    }

    public String getSpotLocation() {
        return SpotLocation;
    }

    public void setSpotLocation(String spotLocation) {
        SpotLocation = spotLocation;
    }

    public String getRecommend() {
        return Recommend;
    }

    public void setRecommend(String recommend) {
        Recommend = recommend;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getSpotArea() {
        return SpotArea;
    }

    public void setSpotArea(String spotArea) {
        SpotArea = spotArea;
    }

    public float getSpotScore() {
        return SpotScore;
    }

    public void setSpotScore(float spotScore) {
        SpotScore = spotScore;
    }

    public int getSoldNumber() {
        return SoldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        SoldNumber = soldNumber;
    }

    public int getSpotPrice() {
        return SpotPrice;
    }

    public void setSpotPrice(int spotPrice) {
        SpotPrice = spotPrice;
    }

    public String getSpotId() {

        return SpotId;
    }

    public void setSpotId(String spotId) {
        SpotId = spotId;
    }


    public String getSpotUrl() {
        return SpotUrl;
    }

    public void setSpotUrl(String spotUrl) {
        SpotUrl = spotUrl;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "SpotName='" + SpotName + '\'' +
                ", SpotCountry='" + SpotCountry + '\'' +
                ", SpotLocation='" + SpotLocation + '\'' +
                ", SpotScore=" + SpotScore +
                ", SoldNumber=" + SoldNumber +
                ", SpotId='" + SpotId + '\'' +
                ", Source='" + Source + '\'' +
                ", Introduce='" + Introduce + '\'' +
                ", SpotUrl='" + SpotUrl + '\'' +
                ", SpotPrice=" + SpotPrice +
                ", SpotArea='" + SpotArea + '\'' +
                ", Recommend='" + Recommend + '\'' +
                '}';
    }
}
