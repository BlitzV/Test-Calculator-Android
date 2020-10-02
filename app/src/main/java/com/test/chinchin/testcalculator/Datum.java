package com.test.chinchin.testcalculator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("st")
    @Expose
    private String st;
    @SerializedName("b")
    @Expose
    private String b;
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("ba")
    @Expose
    private String ba;
    @SerializedName("qa")
    @Expose
    private String qa;
    @SerializedName("i")
    @Expose
    private Double i;
    @SerializedName("ts")
    @Expose
    private Double ts;
    @SerializedName("an")
    @Expose
    private String an;
    @SerializedName("qn")
    @Expose
    private String qn;
    @SerializedName("o")
    @Expose
    private Double o;
    @SerializedName("h")
    @Expose
    private Double h;
    @SerializedName("l")
    @Expose
    private Double l;
    @SerializedName("c")
    @Expose
    private Double c;
    @SerializedName("v")
    @Expose
    private Double v;
    @SerializedName("qv")
    @Expose
    private Double qv;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("as")
    @Expose
    private Double as;
    @SerializedName("pm")
    @Expose
    private String pm;
    @SerializedName("pn")
    @Expose
    private String pn;
    @SerializedName("cs")
    @Expose
    private Integer cs;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("etf")
    @Expose
    private Boolean etf;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getBa() {
        return ba;
    }

    public void setBa(String ba) {
        this.ba = ba;
    }

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public Double getI() {
        return i;
    }

    public void setI(Double i) {
        this.i = i;
    }

    public Double getTs() {
        return ts;
    }

    public void setTs(Double ts) {
        this.ts = ts;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public Double getO() {
        return o;
    }

    public void setO(Double o) {
        this.o = o;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public Double getQv() {
        return qv;
    }

    public void setQv(Double qv) {
        this.qv = qv;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Double getAs() {
        return as;
    }

    public void setAs(Double as) {
        this.as = as;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getEtf() {
        return etf;
    }

    public void setEtf(Boolean etf) {
        this.etf = etf;
    }
}