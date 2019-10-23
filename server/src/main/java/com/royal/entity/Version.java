package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：版本控制模型
* @author Royal
* @date 2019年03月30日 22:56:30
*/
@Table(name="p_version")
public class Version implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *版本号
    */
    @Column(name = "version")
    private String version;
    /**
    *下载地址
    */
    @Column(name = "download_url")
    private String downloadUrl;
    /**
    *1 强制更新 2普通更新
    */
    @Column(name = "upload_type")
    private String uploadType;
    /**
     * ANDROID/IOS
     */
    @Column(name="platform")
    private String platform;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


     public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


     public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


     public String getUploadType() {
        return this.uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }


}