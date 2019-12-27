package com.jd.xq.spring.context;

import com.jd.xq.spring.bean.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
    }

    /**
     * 根据文件名读取xml的配置文件
     *
     * @param fileName Administer
     *                 2013-8-26 下午11:09:16
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

    public Object getBean(String beanName) {
        return sigletons.get(beanName);
    }


}
