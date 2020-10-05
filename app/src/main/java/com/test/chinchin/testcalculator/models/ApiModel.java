package com.test.chinchin.testcalculator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("messageDetail")
    @Expose
    private Object messageDetail;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public ApiModel(String code, Object message, Object messageDetail, List<Datum> data, Boolean success) {
        this.code = code;
        this.message = message;
        this.messageDetail = messageDetail;
        this.data = data;
        this.success = success;
    }

    public ApiModel(List<Datum> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(Object messageDetail) {
        this.messageDetail = messageDetail;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
