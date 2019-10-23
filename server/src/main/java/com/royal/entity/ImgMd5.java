package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：图片过滤模型
* @author Royal
* @date 2019年02月17日 18:35:52
*/
@Table(name="p_img_md5")
public class ImgMd5 implements Serializable {

    /**
    *
    */
    @Id
    @Column(name = "img_id")
    private String imgId;
    /**
    *
    */
    @Column(name = "img_url")
    private String imgUrl;
    /**
    *
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    *
    */
    @Column(name = "type_name")
    private String typeName;


     public String getImgId() {
        return this.imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }


     public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


     public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ImgMd5{" +
                "imgId='" + imgId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", createTime=" + createTime +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}