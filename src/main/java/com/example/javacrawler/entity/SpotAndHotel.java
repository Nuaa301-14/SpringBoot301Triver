package com.example.javacrawler.entity;

import java.util.List;

public class SpotAndHotel {
    //TODO
    /**
     * 产品编号
     */
    private String productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     *  关联的酒店
     */
    private List<String> includeHotel;

    private String hotelsId;

    /**
     * 关联的景区
     */
    private List<String> associateSpot;

    private String spotsId;

    /**
     * 来源
     */
    private String source;

    /**
     * 价格
     */
    private float price;

    /**
     * 分数
     */
    private float score;

    /**
     * 给出的推荐语
     */
    private String recommend;

    /**
     * 供应商
     */
    private String supply;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getHotelsId() {
        return hotelsId;
    }

    public void setHotelsId(String hotelsId) {
        this.hotelsId = hotelsId;
    }

    public String getSpotsId() {
        return spotsId;
    }

    public void setSpotsId(String spotsId) {
        this.spotsId = spotsId;
    }

    public List<String> getIncludeHotel() {
        return includeHotel;
    }

    public void setIncludeHotel(List<String> includeHotel) {
        this.includeHotel = includeHotel;
    }

    public List<String> getAssociateSpot() {
        return associateSpot;
    }

    public void setAssociateSpot(List<String> associateSpot) {
        this.associateSpot = associateSpot;
    }

    @Override
    public String toString() {
        return "SpotAndHotel{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", includeHotel=" + includeHotel +
                ", hotelsId='" + hotelsId + '\'' +
                ", associateSpot=" + associateSpot +
                ", spotsId='" + spotsId + '\'' +
                ", source='" + source + '\'' +
                ", price=" + price +
                ", score=" + score +
                ", recommend='" + recommend + '\'' +
                ", supply='" + supply + '\'' +
                '}';
    }
}
