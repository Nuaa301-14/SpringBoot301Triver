package com.example.javacrawler.entity;

public class InternationalArea {

    private int id;

    private int city_id;

    private String city_name;

    private String english_name;

    private String country;

    private String url;

    private String head;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "InternationalArea{" +
                "id=" + id +
                ", city_id=" + city_id +
                ", city_name='" + city_name + '\'' +
                ", english_name='" + english_name + '\'' +
                ", country='" + country + '\'' +
                ", url='" + url + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
