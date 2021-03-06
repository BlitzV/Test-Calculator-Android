package com.test.chinchin.testcalculator.models;

//model for management of QR data
public class CurrencyModel {

    private String InitValue;
    private String PTR;
    private String BS;
    private String currencyPTR;
    private String currencyBS;

    public CurrencyModel(String initValue, String PTR, String BS, String currencyPTR, String currencyBS) {
        this.InitValue = initValue;
        this.PTR = PTR;
        this.BS = BS;
        this.currencyPTR = currencyPTR;
        this.currencyBS = currencyBS;
    }

    public String getInitValue() {
        return InitValue;
    }

    public void setInitValue(String initValue) {
        InitValue = initValue;
    }

    public String getPTR() {
        return PTR;
    }

    public void setPTR(String PTR) {
        this.PTR = PTR;
    }

    public String getBS() {
        return BS;
    }

    public void setBS(String BS) {
        this.BS = BS;
    }

    public String getCurrencyPTR() {
        return currencyPTR;
    }

    public void setCurrencyPTR(String currencyPTR) {
        this.currencyPTR = currencyPTR;
    }

    public String getCurrencyBS() {
        return currencyBS;
    }

    public void setCurrencyBS(String currencyBS) {
        this.currencyBS = currencyBS;
    }
}
