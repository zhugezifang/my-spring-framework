package com.jd.xq.spring.bean;


import com.jd.xq.spring.annotation.YhdResource;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 11:54
 **/
public class Person {
    @YhdResource(value = "xq")
    private String name;
    @YhdResource(value = "12")
    private String age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
