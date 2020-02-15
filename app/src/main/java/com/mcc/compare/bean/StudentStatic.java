package com.mcc.compare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @CreateDate: 2020/2/6 11:18
 * @Author MCC
 * 学生静态名单存储
 */
public class StudentStatic extends LitePalSupport implements Serializable{

    public StudentStatic() {
    }

    public StudentStatic(String code, String grade, String name) {
        this.code = code;
        this.grade = grade;
        this.name = name;
    }

    //ID
    private String code;
    //例如班级
    private String grade;
    //姓名
    private String name;
    //电话
    private String tel;
    //家庭住址
    private String address;
    //其他信息
    private String info;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StudentStatic{" +
                "code='" + code + '\'' +
                ", grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
