package com.royal.controller;

import com.royal.commen.rong.RYUtil;
import com.royal.commen.rong.models.SdkHttpResult;
import com.royal.entity.enums.ResultEnum;
import com.royal.util.JwtUtil;
import com.royal.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.json.Result;
import com.royal.entity.json.PageData;
import com.royal.entity.User;
import com.royal.service.IUserService;

/**
 * 描述：用户控制层
 *
 * @author Royal
 * @date 2018年12月04日 14:19:32
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 描述：分页 查询
     */
    @RequestMapping(value = "/getUserList")
    @ResponseBody
    public Result getPage(User user) throws Exception {
        try {
            PageData pd = this.getPageData ();
            PageInfo<User> list = userService.getPage (user, pd);
            return new Result (list);
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述：根据LoginName 查询
     *
     * @param vo 用户id
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Result findById(User vo) throws Exception {
        try {
            vo.setLoginName (JwtUtil.getUser (this.getRequest ()));
            User user = userService.findByLoginName (vo, vo.getLoginName ());
            user.setPassword (null);
            return new Result (user);
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述:创建用户
     *
     * @param user 用户
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result create(User user) throws Exception {
        try {
            userService.add (user);
            return new Result ();
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：删除用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result deleteById(User user) throws Exception {
        try {
            userService.delete (user);
            return new Result ();
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：更新用户
     *
     * @param user 用户id
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Result updateUser(User user) throws Exception {
        try {
            if (Tools.isEmpty (user.getNickname (), user.getUserImg ())) {
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            User u = new User ();
            u.setNickname (user.getNickname ());
            u.setUserImg (user.getUserImg ());
            u.setLoginName (JwtUtil.getUser (this.getRequest ()));
            SdkHttpResult r = RYUtil.refreshUser (u.getLoginName (), u.getNickname (), u.getUserImg ());
            if (r.getHttpCode () != 200) {
                logger.error ("User出异常了" + r.getResult ());
                return new Result (ResultEnum.UPDATE_ERROR, r.getResult ());
            }
            userService.updateByLoginName (u);
            return new Result (true);
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：实名认证
     *
     * @param user 用户id
     */
    @RequestMapping(value = "/certification")
    @ResponseBody
    public Result certification(User user) throws Exception {
        try {
            if (Tools.isEmpty (user.getIdNumber (), user.getGender (), user.getBirthdate (), user.getCardReverse (),
                    user.getCardFront (), user.getUserName ())) {
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            User u = new User ();
            if(!user.getGender ().equals("F")&&!user.getGender ().equals("M")){
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            if(user.getIdNumber ().indexOf("*")>=0){
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            u.setLoginName (JwtUtil.getUser (this.getRequest ()));
            u.setIdNumber (user.getIdNumber ());
            u.setGender (user.getGender ());
            u.setBirthdate (user.getBirthdate ());
            u.setCardReverse (user.getCardReverse ());
            u.setCardFront (user.getCardFront ());
            u.setUserName (user.getUserName ());
            u.setAuditStatus ("NO_AUDIT");
//            user.setAuditStatus("VERIFIED");
            userService.updateByLoginName (u);
            return new Result (true);
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：绑卡
     *
     * @param user 用户id
     */
    @RequestMapping(value = "/bank")
    @ResponseBody
    public Result bank(User user) throws Exception {
        try {
            if (Tools.isEmpty (user.getBranch (), user.getBankCard (), user.getBankAddress (), user.getBankOfDeposit ())) {
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            User u = new User ();
            u.setBranch (user.getBranch ());
            u.setBankAddress (user.getBankAddress ());
            u.setBankCard (user.getBankCard ());
            u.setBankOfDeposit(user.getBankOfDeposit());
            u.setLoginName (JwtUtil.getUser (this.getRequest ()));
            userService.updateByLoginName (u);
            return new Result (true);
        } catch (Exception e) {
            logger.error ("User出异常了", e);
            return new Result (e);
        }
    }

}