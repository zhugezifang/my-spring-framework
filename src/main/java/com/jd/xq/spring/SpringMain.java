package com.jd.xq.spring;

import com.jd.xq.spring.bean.Person;
import com.jd.xq.spring.context.XqClassPathXmlApplicationContext;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 11:52
 **/
public class SpringMain {
    public static void main(String[] args) {
        XqClassPathXmlApplicationContext xqClassPathXmlApplicationContext=new XqClassPathXmlApplicationContext("spring.xml");
        Person person=(Person) xqClassPathXmlApplicationContext.getBean("person");
        System.out.println(person.getName());
    }
}
