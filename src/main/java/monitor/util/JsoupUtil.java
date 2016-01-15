/*
 * Copyright (c) 2015 Sohu TV. All rights reserved.
 */
package monitor.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * <P>
 * Description: 对开源工具jsoup进行简单封装
 * </p>
 * @author wb-jianlin
 * @version 1.0
 * @Date 2015年9月23日下午12:50:37
 */
public class JsoupUtil {

    /**
     * 获取一个Document对象
     * @param url
     * @return
     */
    public static Document getDoc(String url) {
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            if (document == null) {
                document = Jsoup.connect(url).timeout(10000).get();
            }
            return document;
        } catch (IOException e) {
            System.out.println("get document error," + e.getMessage());
        }
        return null;
    }



}
