package com.study.day01.util;

public enum CountryEnum {
    One(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"韩"),
    SIX(6,"魏");


    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public java.lang.String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    private  int retCode;
    private  String retMsg;

    CountryEnum(int retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static CountryEnum forEachRetMsg(int retCode){
        CountryEnum countryEnum[]= CountryEnum.values();
        for (CountryEnum c:countryEnum) {
            if(c.retCode==retCode){
                return c;
            }
        }
        return null;
    }
}
