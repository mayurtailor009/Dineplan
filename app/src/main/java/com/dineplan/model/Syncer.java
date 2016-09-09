package com.dineplan.model;

/**
 * Created by sandeepjoshi on 04/09/16.
 */
public class Syncer{
    private int id;
    private String name;
    private String syncerGuid;
    private int tenantId;
    private boolean isDeleted;
    private int deleterUserId;
    private String deletionTime;
    private String lastModificationTime;
    private int lastModifierUserId;
    private String creationTime;
    private int creatorUserId;
    private boolean isSyncNeeded;


    public boolean isSyncNeeded() {
        return isSyncNeeded;
    }

    public void setSyncNeeded(boolean syncNeeded) {
        isSyncNeeded = syncNeeded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSyncerGuid() {
        return syncerGuid;
    }

    public void setSyncerGuid(String syncerGuid) {
        this.syncerGuid = syncerGuid;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(int deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public String getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(String deletionTime) {
        this.deletionTime = deletionTime;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public int getLastModifierUserId() {
        return lastModifierUserId;
    }

    public void setLastModifierUserId(int lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
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
}
