package com.dss.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class Fund implements Comparable<Fund> {
    private String name;
    private String code;
    private String dateString;
    private float price;

    private Date date;

    @Override
    public String toString() {
        return "Fund{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", dateString='" + dateString + '\'' +
                ", price=" + price +
                '}';
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

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Fund o) {
        return date.before(o.getDate()) ? -1 : 1;
    }
}
