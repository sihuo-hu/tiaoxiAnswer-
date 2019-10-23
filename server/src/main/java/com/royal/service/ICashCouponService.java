package com.royal.service;

import com.royal.entity.CashCoupon;

import java.math.BigDecimal;

/**
* 描述：代金券 服务实现层接口
* @author Royal
* @date 2019年05月23日 19:35:09
*/
public interface ICashCouponService extends BaseService<CashCoupon> {

    /**
     * 发放代金券
     * @param loginName
     * @param grantCondition 自动发放的类型
     * @param payMoney 达标金额
     */
    BigDecimal pushCashCoupon(String loginName, String grantCondition, BigDecimal payMoney);
}