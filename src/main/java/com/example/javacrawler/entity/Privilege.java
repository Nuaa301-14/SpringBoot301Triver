package com.example.javacrawler.entity;

import java.util.Date;

public class Privilege {

    private Integer privId;

    private String name;

    private String code;

    private String url;

    private Integer parentId;

    private Date created;

    private Date updated;

    private Integer isParent;

    public Integer getPrivId() {
        return privId;
    }

    public void setPrivId(Integer privId) {
        this.privId = privId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "privId=" + privId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", parentId=" + parentId +
                ", created=" + created +
                ", updated=" + updated +
                ", isParent=" + isParent +
                '}';
    }
}
