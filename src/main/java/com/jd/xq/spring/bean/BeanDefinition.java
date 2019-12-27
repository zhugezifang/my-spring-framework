package com.jd.xq.spring.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 11:46
 **/
public class BeanDefinition {
    private String id;//bean的id
    private String className;//bean的类

    private List<PropertyDefinition> propertyDefinitions=new ArrayList<PropertyDefinition>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public List<PropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public void setPropertyDefinitions(List<PropertyDefinition> propertyDefinitions) {
        this.propertyDefinitions = propertyDefinitions;
    }
}
