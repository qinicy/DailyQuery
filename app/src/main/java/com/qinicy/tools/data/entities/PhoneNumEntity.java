package com.qinicy.tools.data.entities;

/**
 * Created by qinicy on 16/2/25.
 */
public class PhoneNumEntity {
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区号
     */
    private String areacode;
    /**
     * 邮编
     */
    private String zip;
    /**
     * 运营商
     */
    private String company;
    /**
     * 卡种

     */
    private String card;


    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


}
