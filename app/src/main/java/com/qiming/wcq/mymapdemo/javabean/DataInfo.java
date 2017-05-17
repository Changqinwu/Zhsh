package com.qiming.wcq.mymapdemo.javabean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class DataInfo {

    private String str;
    private int intId;
    private String describe;

    public DataInfo(String str, int intId, String describe) {
        this.str = str;
        this.intId = intId;
        this.describe = describe;
    }

    @Override
    public String toString() {

        return "DataInfo{" +
                "str='" + str + '\'' +
                ", intId=" + intId +
                ", describe='" + describe + '\'' +
                '}';
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }
}
