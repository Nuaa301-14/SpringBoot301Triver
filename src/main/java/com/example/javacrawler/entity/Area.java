package com.example.javacrawler.entity;

public class Area {

    private int id;

    private int city_id;

    private String city_name;

    private String head_pinyin;

    private String pinyin;

    private String url;

    private String resource;

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

    public String getHead_pinyin() {
        return head_pinyin;
    }

    public void setHead_pinyin(String head_pinyin) {
        this.head_pinyin = head_pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", city_id=" + city_id +
                ", city_name='" + city_name + '\'' +
                ", head_pinyin='" + head_pinyin + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", url='" + url + '\'' +
                ", resource='" + resource + '\'' +
                '}';
    }
}
