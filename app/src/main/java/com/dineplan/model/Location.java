package com.dineplan.model;

/**
 * Created by sandeepjoshi on 03/09/16.
 */
public class Location {

    private int companyRefId;
    private String code;
    private String name;
    private int sharingPercentage;
    private int defaultRequestLocationRefId;
    private boolean isDeleted;
    private String creationTime;
    private int creatorUserId;
    private int id;
    private boolean selected;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCompanyRefId() {
        return companyRefId;
    }

    public void setCompanyRefId(int companyRefId) {
        this.companyRefId = companyRefId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSharingPercentage() {
        return sharingPercentage;
    }

    public void setSharingPercentage(int sharingPercentage) {
        this.sharingPercentage = sharingPercentage;
    }

    public int getDefaultRequestLocationRefId() {
        return defaultRequestLocationRefId;
    }

    public void setDefaultRequestLocationRefId(int defaultRequestLocationRefId) {
        this.defaultRequestLocationRefId = defaultRequestLocationRefId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
