package com.oops.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author L-N
 * @Description 对资源配置文件进行类映射
 * @createTime 2021年01月31日 15:10
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-prod.properties")
public class FileUpload {
    private String userFaceLocation;
    private String imageServerUrl;

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }

    public String getUserFaceLocation() {
        return userFaceLocation;
    }

    public void setUserFaceLocation(String userFaceLocation) {
        this.userFaceLocation = userFaceLocation;
    }
}
