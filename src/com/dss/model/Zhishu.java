package com.dss.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class Zhishu implements Comparable<Zhishu> {
    private String name;
    private String dateString;
    private float value;

    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Zhishu{" +
                "name='" + name + '\'' +
                ", dateString='" + dateString + '\'' +
                ", value=" + value +
                '}';
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Zhishu o) {
        return date.before(o.getDate()) ? -1 : 1;
    }
}
