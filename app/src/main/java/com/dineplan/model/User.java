package com.dineplan.model;

/**
 * Created by sandeepjoshi on 31/08/16.
 */
public class User {

    private int userId;
    private int tantantId;
    private boolean success;
    private String result;
    private String error;
    private boolean unAuthorizedRequest;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTantantId() {
        return tantantId;
    }

    public void setTantantId(int tantantId) {
        this.tantantId = tantantId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
        this.unAuthorizedRequest = unAuthorizedRequest;
    }
}
