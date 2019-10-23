package com.royal.service;

import com.royal.entity.VerificationCode;
import com.royal.entity.json.Result;

/**
* 描述：验证码 服务实现层接口
* @author Royal
* @date 2018年12月04日 13:52:12
*/
public interface IVerificationCodeService extends BaseService<VerificationCode> {


    Result verification(VerificationCode verificationCode);

    Integer deleteByLoginNameAndCodeType(VerificationCode verificationCode);
}