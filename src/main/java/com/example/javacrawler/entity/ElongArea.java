package com.example.javacrawler.entity;

public class ElongArea {
    private int cityId;

    private String cityNameEn;

    private String cityNameCn;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getCityNameCn() {
        return cityNameCn;
    }

    public void setCityNameCn(String cityNameCn) {
        this.cityNameCn = cityNameCn;
    }

    @Override
    public String toString() {
        return "ElongArea{" +
                "cityId=" + cityId +
                ", cityNameEn='" + cityNameEn + '\'' +
                ", cityNameCn='" + cityNameCn + '\'' +
                '}';
    }
}
