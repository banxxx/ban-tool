package cn.banxx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 腾讯云COS上传配置类.
 *
 * @author : Ban
 * @createTime: 2023-12-12  01:15
 * @version : 1.0
 * @since : 1.0
 */
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class CosConfig {

    /**
     * 腾讯云控制台项目配置secretId
     */
    private String secretId;
    /**
     * 腾讯云控制台项目配置secretKey
     */
    private String secretKey;
    /**
     * 存储桶地域
     */
    private String region;
    /**
     * 存储桶名称
     */
    private String bucketName;
    /**
     * 业务项目名称
     */
    private String projectName;
    /**
     * 企业名称
     */
    private String common;
    /**
     * 图片大小
     */
    private String imageSize;
    /**
     * CDN加速域名
     */
    private String prefixDomain;
    /**
     *
     */
    private Long expiration;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getPrefixDomain() {
        return prefixDomain;
    }

    public void setPrefixDomain(String prefixDomain) {
        this.prefixDomain = prefixDomain;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }


}
