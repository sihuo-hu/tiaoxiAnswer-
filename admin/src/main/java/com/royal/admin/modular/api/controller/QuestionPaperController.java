/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.royal.admin.modular.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.core.common.annotion.BussinessLog;
import com.royal.admin.core.common.constant.dictmap.NoticeMap;
import com.royal.admin.core.common.constant.factory.ConstantFactory;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.log.LogObjectHolder;
import com.royal.admin.core.shiro.ShiroKit;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.service.QuestionPaperService;
import com.royal.admin.modular.system.entity.Notice;
import com.royal.admin.modular.system.service.NoticeService;
import com.royal.admin.modular.system.warpper.NoticeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 试卷控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/questionPaper")
public class QuestionPaperController extends BaseController {

    private String PREFIX = "/modular/business/questionPaper/";

    @Autowired
    private QuestionPaperService questionPaperService;

    /**
     * 跳转到试卷列表首页
     *
     * @author royal
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 获取试卷列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Page<Map<String, Object>> list = questionPaperService.selectPageList(beginTime, endTime);
        return LayuiPageFactory.createPageInfo(list);
    }

    /**
     * 获取试卷列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/all")
    @ResponseBody
    public Object all() {
        List<QuestionPaperModel> list = questionPaperService.list();
        return list;
    }


    /**
     * 跳转到添加试卷
     *
     * @author royal
     */
    @RequestMapping("/to_add")
    public String noticeAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 新增试卷
     *
     * @author royal
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String title,String activityTime,Integer awardCount) {
        if (ToolUtil.isOneEmpty(title, activityTime)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        String[] split = activityTime.split(" - ");
        String beginTime = split[0];
        String endTime = split[1];
        QuestionPaperModel questionPaperModel = new QuestionPaperModel();
        questionPaperModel.setEndTime(DateUtils.StringToDate(endTime));
        questionPaperModel.setStartTime(DateUtils.StringToDate(beginTime));
        questionPaperModel.setAwardCount(awardCount);
        questionPaperModel.setTitle(title);
        this.questionPaperService.save(questionPaperModel);
        return SUCCESS_TIP;
    }

    /**
     * 删除试卷
     *
     * @author royal
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String id) {
        this.questionPaperService.removeById(id);
        return SUCCESS_TIP;
    }



}
