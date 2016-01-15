package monitor.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2015/1/8.
 */
public class HttpUtil {

    /**
     * 返回一个HttpClient
     *
     * @return
     */
    public static CloseableHttpClient fetchHttpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig()).build();
    }

    /**
     * <P>
     * Description:post请求
     * </p>
     *
     * @param postUrl 请求的url
     * @param params  post传递的参数按照K-V的结果set到这个Map中
     * @return
     * @author wb-jianlin
     * @version 1.0
     * @Date 2015年9月18日上午11:06:59
     */
    public static String post(String postUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig()).build();
        HttpPost post = new HttpPost(postUrl);
        String response = null;
        CloseableHttpResponse res = null;
        try {
            List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
            if (params != null && !params.isEmpty()) {
                Iterator<Map.Entry<String, Object>> iterEntry = params.entrySet().iterator();
                Map.Entry<String, Object> entry;
                while (iterEntry.hasNext()) {
                    entry = iterEntry.next();
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    formParams.add(new BasicNameValuePair(key, value));
                }
            }
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            post.setEntity(entity);
            res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                response = EntityUtils.toString(res.getEntity());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                System.out.println("httpUtil post error, " + e.getMessage());
            }
        }
        return response;
    }

    /**
     * <P>
     * Description: post请求
     * </p>
     *
     * @param postUrl
     * @param postData 传入String类型的参数，部分商店需要传入json格式的字符串用这个方法
     * @return
     * @author wb-jianlin
     * @version 1.0
     * @Date 2015年9月18日上午11:08:34
     */
    public static String post(String postUrl, String postData) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig()).build();
        HttpPost post = new HttpPost(postUrl);

        String response = null;
        CloseableHttpResponse res = null;
        try {
            HttpEntity httpEntity = new StringEntity(postData, "UTF-8");
            post.setEntity(httpEntity);
            res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                response = EntityUtils.toString(res.getEntity());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                System.out.println("httpUtil post error, " + e.getMessage());
            }
        }
        return response;
    }

    /**
     * <P>
     * Description:附带特殊的头信息请求post提交
     * </p>
     *
     * @param postUrl
     * @param headers  头信息，例如Accept-Encoding等
     * @param postData 传入数据
     * @return
     * @author wb-jianlin
     * @version 1.0
     * @Date 2015年9月21日上午10:22:33
     */
    public static String post(String postUrl, Header[] headers, String postData) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig()).build();
        HttpPost post = new HttpPost(postUrl);
        if (headers != null) {
            for (Header header : headers) { // 需要添加传入的头信息
                post.setHeader(header);
            }
        }
        String response = null;
        CloseableHttpResponse res = null;
        try {
            HttpEntity httpEntity = new StringEntity(postData, "UTF-8");
            post.setEntity(httpEntity);
            res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                response = EntityUtils.toString(res.getEntity());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                System.out.println("httpUtil post error, " + e.getMessage());
            }
        }
        return response;
    }

    /**
     * <P>
     * Description:get请求
     * </p>
     *
     * @param getUrl
     * @return
     * @author wb-jianlin
     * @version 1.0
     * @Date 2015年9月18日上午11:08:05
     */
    public static String get(String getUrl) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig()).build();
        HttpGet httpGet = new HttpGet(getUrl);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                return getRetEntityStr(httpResponse);
            } else {
                System.out.println("httpUtil request [" + getUrl + "] error, error code"
                        + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                System.out.println("httpUtil get error, " + e.getMessage());
            }
        }
        return null;
    }

    public static String getRetEntityStr(HttpResponse response) throws IOException {
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    public static void close(CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * httpClient配置相关参数 超时时间设置为20秒
     *
     * @return
     */
    private static RequestConfig getRequestConfig() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(20000)
                .setStaleConnectionCheckEnabled(true)
                .build();
        return defaultRequestConfig;
    }
}
