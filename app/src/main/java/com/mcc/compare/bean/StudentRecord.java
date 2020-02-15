package com.mcc.compare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * @CreateDate: 2020/2/6 18:30
 * @Author MCC
 * 学生比对信息存储
 */
public class StudentRecord extends LitePalSupport implements Serializable {


    public StudentRecord() {
    }

    public StudentRecord(String grade, int state, String codeInStatic, String name) {
        this.grade = grade;
        this.state = state;
        this.codeInStatic = codeInStatic;
        this.name = name;
    }

    public StudentRecord(String grade, int state, String name) {
        this.grade = grade;
        this.state = state;
        this.name = name;
    }

    public StudentRecord(String marks, String grade, int state, String name, Date date) {
        this.marks = marks;
        this.grade = grade;
        this.state = state;
        this.name = name;
        this.date = date;
    }
    //数据库中的编码
    private String code;
    //例如作业对比
    private String marks;
    //班级
    private String grade;
    //比对状态
    private int state;
    //在静态名单中的编码
    private String codeInStatic;
    //名字
    private String name;
    //记录备注
    private String info;
    //记录日期
    private Date date;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeInStatic() {
        return codeInStatic;
    }

    public void setCodeInStatic(String codeInStatic) {
        this.codeInStatic = codeInStatic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StudentRecord{" +
                "code='" + code + '\'' +
                ", marks='" + marks + '\'' +
                ", grade='" + grade + '\'' +
                ", state=" + state +
                ", codeInStatic='" + codeInStatic + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", date=" + date +
                '}';
    }
}
