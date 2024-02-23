package cn.banxx.file;

import cn.banxx.config.CosConfig;
import cn.banxx.constant.TencentCosConstant;
import cn.banxx.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 腾讯云COS上传工具类.
 *
 */
// @Configuration
// @ConditionalOnProperty(value = "tencent.css", havingValue = "true")
// @EnableConfigurationProperties(CosConfig.class)
public class TencentCosUtil {

    private static CosConfig config;

    @Autowired
    private CosConfig configs;

    @PostConstruct
    public void initialize() {
        config = this.configs;
    }

    // @Autowired
    // public void setConfig(CosConfig config){
    //     TencentCosUtil.config = config;
    // }

    public static String getTemporaryAccessCredentials() throws UnsupportedEncodingException {
        // {"effect":"allow","action":"sts:AssumeRole","resource":"*"}
        JSONObject jsonOne = new JSONObject();
        jsonOne.put("effect","allow");
        jsonOne.put("action","sts:AssumeRole");
        jsonOne.put("resource","*");
        JSONObject[] statement = new JSONObject[]{jsonOne};
        JSONObject jsonTwo = new JSONObject();
        jsonTwo.put("version","2.0");
        jsonTwo.put("statement",statement);
        String encodedUrl = URLEncoder.encode(jsonTwo.toString(),"UTF-8");
        String format = String.format(TencentCosConstant.serialVersionUID,config.getRegion(),encodedUrl);
        net.sf.json.JSONObject jsonObject = HttpUtil.sendGet(format);
        return jsonObject.toString();
    };

}
