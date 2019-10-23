package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 描述：开关模型
* @author Royal
* @date 2019年02月21日 16:36:03
*/
@Table(name="p_switch")
@Data
public class Switch implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *ANDROID/IOS
    */
    @Column(name = "platform")
    private String platform;

    @Column(name="ditch")
    private String ditch;
    /**
    *
    */
    @Column(name = "versions")
    private String versions;
    /**
    *交易
    */
    @Column(name = "transaction")
    private String transaction;
    /**
    *资金
    */
    @Column(name = "capital")
    private String capital;


}