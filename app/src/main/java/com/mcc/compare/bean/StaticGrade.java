package com.mcc.compare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @CreateDate: 2020/2/7 19:43
 * @Author MCC
 * 记录学生静态名单信息
 */
public class StaticGrade extends LitePalSupport implements Serializable {

    private String code;

    private String grade;

    public StaticGrade(String code, String grade) {
        this.code = code;
        this.grade = grade;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StaticGrade{" +
                "code='" + code + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
