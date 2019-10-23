package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：banner图模型
* @author Royal
* @date 2018年12月06日 16:24:12
*/
@Table(name="b_banner")
public class Banner implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *图片地址
    */
    @Column(name = "img_address")
    private String imgAddress;
    /**
    *连接地址
    */
    @Column(name = "link")
    private String link;
    /**
    *描述
    */
    @Column(name = "banner_desc")
    private String bannerDesc;
    /**
    *排序号 1在最前面，9在最后面
    */
    @Column(name = "sort_no")
    private Integer sortNo;
    /**
    *创建时间
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


     public String getImgAddress() {
        return this.imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }


     public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }


     public String getBannerDesc() {
        return this.bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc;
    }


    public Integer getSortNo() {
        return this.sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", imgAddress='" + imgAddress + '\'' +
                ", link='" + link + '\'' +
                ", bannerDesc='" + bannerDesc + '\'' +
                ", sortNo=" + sortNo +
                ", createTime=" + createTime +
                '}';
    }
}