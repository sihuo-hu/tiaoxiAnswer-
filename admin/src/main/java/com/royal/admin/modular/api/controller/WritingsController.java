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

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.WritingsModel;
import com.royal.admin.modular.api.service.QuestionPaperService;
import com.royal.admin.modular.api.service.WritingsPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 章节控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/writings")
public class WritingsController extends BaseController {

    private String PREFIX = "/modular/business/writings/";

    @Autowired
    private WritingsPaperService writingsService;

    /**
     * 跳转到章节列表首页
     *
     * @author royal
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 获取章节列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Page<Map<String, Object>> list = writingsService.selectPageList();
        return LayuiPageFactory.createPageInfo(list);
    }


    /**
     * 跳转到添加章节
     *
     * @author royal
     */
    @RequestMapping("/to_add")
    public String noticeAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 新增章节
     *
     * @author royal
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WritingsModel writingsModel) {
        if (ToolUtil.isOneEmpty(writingsModel.getUserCount(), writingsModel.getWName(),writingsModel.getSortNumber(),writingsModel.getQId())||writingsModel.getQId().equals("0")) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.writingsService.save(writingsModel);
        return SUCCESS_TIP;
    }

    /**
     * 删除章节
     *
     * @author royal
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String id) {
        this.writingsService.removeById(id);
        return SUCCESS_TIP;
    }

    /**
     * 根据活动获取章节列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getQId")
    @ResponseBody
    public Object getQId(String qId) {
        QueryWrapper<WritingsModel> writingsModelQueryWrapper = new QueryWrapper<>();
        writingsModelQueryWrapper.eq("b_q_id",qId);
        List<WritingsModel> list = writingsService.list(writingsModelQueryWrapper);
        return list;
    }

}
