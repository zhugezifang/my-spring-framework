package com.jd.xq.spring;

import com.jd.xq.spring.utils.XmlParseUtil;
import org.junit.Test;

import java.io.File;

/**
 * @author duanxiaoqiu
 * @Date 2019-12-27 11:42
 **/
public class XmlTest {

    @Test
    public void readXml() {
        String xmlPath = "src/main/resources/spring.xml";
        xmlPath = new File(xmlPath).getAbsolutePath();
        XmlParseUtil.parseXml(xmlPath);
    }
}
