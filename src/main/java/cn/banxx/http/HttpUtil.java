package cn.banxx.http;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * 网络请求工具类.
 */
public class HttpUtil {

    public static JSONObject sendGet(String url) {
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static JSONObject sendPost(String url, JSONObject jsonParam) {
        return sendPost(url, jsonParam.toString(), false);
    }


    public static JSONObject sendPost(String url, String json, boolean noNeedResponse) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != json) {
                StringEntity entity = new StringEntity(json, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
