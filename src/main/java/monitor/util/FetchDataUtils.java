package monitor.util;

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianlin210349 on 2015/12/30.
 */
public class FetchDataUtils {
    public static Map<String, String> getData(String url) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            Document document = JsoupUtil.getDoc(url);
            map.put("areaLevel", document.select("p.cm_area_level").first().child(0).text()); //空气质量
            map.put("AQI", document.select("p.cm_area_big").first().text());
            map.put("pm2.5", document.select("p.cm_nongdu").first().text());
            map.put("tips1", document.select("div.c_item").first().getElementsByTag("p").first().text());
            map.put("tips2", document.select("div.c_item").get(1).getElementsByTag("p").first().text());
            return map;
        } catch (Exception e) {
            System.out.println("fetch data error, " + e.getMessage());
            return null;
        }
    }
}
