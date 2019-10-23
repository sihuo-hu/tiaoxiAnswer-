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
* 描述：代金券发放日志模型
* @author Royal
* @date 2019年06月27日 09:53:36
*/
@Data
@Table(name="b_cash_coupon_push_log")
public class CashCouponPushLog implements Serializable {

    /**
    *
    */
    @Column(name = "cash_coupon_id")
    private String cashCouponId;
    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private String id;
    /**
    *
    */
    @Column(name = "create_time")
    private String createTime;
    /**
    *
    */
    @Column(name = "operator")
    private String operator;
    /**
    *
    */
    @Column(name = "target_population")
    private String targetPopulation;
    /**
    *
    */
    @Column(name = "login_names")
    private String loginNames;
    /**
    *
    */
    @Column(name = "push_count")
    private Integer pushCount;

}