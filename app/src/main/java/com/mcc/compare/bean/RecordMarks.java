package com.mcc.compare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @CreateDate: 2020/2/7 19:47
 * @Author MCC
 */
public class RecordMarks extends LitePalSupport implements Serializable {

    private String code;

    private String marks;

    private Date date;

    public RecordMarks(String code, String marks, Date date) {
        this.code = code;
        this.marks = marks;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RecordMarks{" +
                "code='" + code + '\'' +
                ", marks='" + marks + '\'' +
                ", date=" + date +
                '}';
    }
}
