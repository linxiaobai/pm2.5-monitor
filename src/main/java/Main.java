import monitor.util.Constants;
import monitor.util.FetchDataUtils;
import monitor.util.PushUtils;

import java.util.Map;

/**
 * Created by jianlin210349 on 2015/12/30.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String> tjData = FetchDataUtils.getData(Constants.TJ_URL);
        Map<String, String> bjData = FetchDataUtils.getData(Constants.BJ_URL);
        String content = buildContent(tjData, "【天津】");
        content += buildContent(bjData, "【北京】");
        PushUtils.push("天气状况播报", content, "");
    }


    private static String buildContent(Map<String, String> data, String area) {
        StringBuffer content = new StringBuffer();
        content.append("======").append(area).append("地区空气指数======").append("\n");
        content.append("环境状况: ").append(data.get("areaLevel")).append("\n");
        content.append("AQI指数: ").append(data.get("AQI")).append("\n");
        content.append("pm2.5情况: ").append(data.get("pm2.5")).append("\n");
        content.append("今日情况概述: ").append(data.get("tips1")).append("\n");
        content.append("温馨提示: ").append(data.get("tips2")).append("\n");
        return content.toString();
    }
}
