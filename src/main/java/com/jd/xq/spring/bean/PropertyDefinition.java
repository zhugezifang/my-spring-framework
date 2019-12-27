package com.jd.xq.spring.bean;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 12:10
 **/
public class PropertyDefinition {
    private String name;
    private String value;

    public PropertyDefinition(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
