package com.jd.xq.spring.context;

import com.jd.xq.spring.bean.BeanDefinition;
import com.jd.xq.spring.bean.PropertyDefinition;
import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.*;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 11:38
 **/
public class XqClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();//用来存储所有的beans
    private Map<String, Object> sigletons = new HashMap<String, Object>();//用来存储实例化后的bean

    /**
     * 构造方法，用来模拟spring的行为
     *
     * @param fileName
     */
    public XqClassPathXmlApplicationContext(String fileName) {
        //1.read xml
        this.readXml(fileName);
        //2.实例化bean
        this.instanceBeans();
        //3.实现对依赖对象的注入功能
        this.injectObject();
    }

    /**
     * 根据文件名读取xml的配置文件
     *
     */
    private void readXml(String fileName) {
        // TODO Auto-generated method stub
        //创建一个读取器
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            //获取要读取的配置文件的路径
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            //读取文件内容
            document = saxReader.read(xmlPath);
            //获取xml中的根元素
            Element rootElement = document.getRootElement();
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue("id");//获取bean的id属性值
                String clazz = element.attributeValue("class");//获取bean的class属性值
                BeanDefinition beanDefinition = new BeanDefinition(id, clazz);
                //获取bean的Property属性
                for (Iterator subElementIterator = element.elementIterator(); subElementIterator.hasNext(); ) {
                    Element subElement = (Element) subElementIterator.next();
                    String propertyName = subElement.attributeValue("name");
                    String propertyValue = subElement.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyValue);
                    beanDefinition.getPropertyDefinitions().add(propertyDefinition);
                }
                beanDefines.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实例化bean
     */
    private void instanceBeans() {
        if (beanDefines != null && beanDefines.size() > 0) {
            //对每个bean进行实例化
            for (BeanDefinition beanDefinition : beanDefines) {
                try {
                    //bean的class属性存在的时候才进行实例化，否则不进行实例化
                    if (beanDefinition.getClassName() != null && !beanDefinition.getClassName().equals("")) {
                        //实例化的关键操作
                        sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
                        System.out.println("id为：" + beanDefinition.getId() + "的bean实例化成功");
                    }
                } catch (Exception e) {
                    System.out.println("bean实例化失败");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为bean对象的属性注入值
     * <p>
     */
    private void injectObject() {
        //遍历配置文件中定义的所有的bean
        for (BeanDefinition beanDefinition : beanDefines) {
            //找到要注入的bean
            Object bean = sigletons.get(beanDefinition.getId());
            if (bean != null) {
                try {
                    BeanInfo info = Introspector.getBeanInfo(bean.getClass());//通过类Introspector的getBeanInfo方法获取对象的BeanInfo 信息
                    //通过BeanInfo来获取属性的描述器(PropertyDescriptor),通过这个属性描述器就可以获取某个属性对应的getter/setter方法,然后我们就可以通过反射机制来调用这些方法。
                    PropertyDescriptor[] pds = info.getPropertyDescriptors();//获得 bean所有的属性描述
                    //遍历要注入的bean的所有属性
                    for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {
                        //遍历要注入bean通过属性描述器得到的所有属性以及行为
                        for (PropertyDescriptor propertyDescriptor : pds) {
                            //用户定义的bean属性与java内省后的bean属性名称相同时
                            if (propertyDefinition.getName().equals(propertyDescriptor.getName())) {
                                Method setter = propertyDescriptor.getWriteMethod();//获取属性的setter方法
                                //取到了setter方法
                                if (setter != null) {
                                    Object value = null;//用来存储引用的值
                                    //ConvertUtil依赖两个jar包，一个是common-beanutils,而common-beanutils又依赖common-logging
                                    //ConvertUtil将任意类型转化为需要的类型
                                    value = ConvertUtils.convert(propertyDefinition.getValue(), propertyDescriptor.getPropertyType());
                                    setter.setAccessible(true);//保证setter方法可以访问私有
                                    try {
                                        setter.invoke(bean, value);//把引用对象注入到属性
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;//找到了注入的属性后，跳出循环
                            }
                        }
                    }
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Object getBean(String beanName) {
        return sigletons.get(beanName);
    }


}
