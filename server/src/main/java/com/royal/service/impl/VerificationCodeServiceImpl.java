package com.royal.service.impl;

import javax.annotation.Resource;

import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.Result;
import com.royal.util.DateUtils;
import com.royal.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.VerificationCode;
import com.royal.mapper.VerificationCodeMapper;
import com.royal.service.IVerificationCodeService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;


/**
 * 描述：验证码 服务实现层
 *
 * @author Royal
 * @date 2018年12月04日 13:52:12
 */
@Service
@Transactional
public class VerificationCodeServiceImpl extends BaseServiceImpl<VerificationCode> implements IVerificationCodeService {

    private VerificationCodeMapper verificationCodeMapper;

    @Resource
    public void setBaseMapper(VerificationCodeMapper mapper) {
        super.setBaseMapper(mapper);
        this.verificationCodeMapper = mapper;
    }

    /**
     * 验证验证码是否正确
     *
     * @param verificationCode
     */
    @Override
    public Result verification(VerificationCode verificationCode) {
        VerificationCode vc = verificationCodeMapper.selectByPrimaryKey(verificationCode.getId());
        if (vc == null || Tools.isEmpty(vc.getId()) || !verificationCode.getLoginName().equals(vc.getLoginName()) || !verificationCode.getVerificationCode().equals(vc.getVerificationCode())) {
            return new Result(ResultEnum.VERIFICATION_CODE_ERROR);
        }

        if(!DateUtils.belongCalendar(new Date(),vc.getCreateTime(),vc.getExpirationTime())){
            return new Result(ResultEnum.VERIFICATION_CODE_INVALID);
        }
        return new Result(ResultEnum.SUCCEED);
    }

    /**
     * 删除该用户的多余验证码
     * @param verificationCode
     * @return
     */
    @Override
    public Integer deleteByLoginNameAndCodeType(VerificationCode verificationCode) {
        Example ex = new Example(VerificationCode.class);
        ex.createCriteria().andEqualTo("loginName",verificationCode.getLoginName()).andEqualTo("codeType",
                verificationCode.getCodeType());
        return verificationCodeMapper.deleteByExample(ex);
    }
}