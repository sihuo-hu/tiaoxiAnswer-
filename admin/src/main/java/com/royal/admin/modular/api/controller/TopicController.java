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
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.core.common.constant.Const;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.*;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.TopicModel;
import com.royal.admin.modular.api.json.TopicJson;
import com.royal.admin.modular.api.service.QuestionPaperService;
import com.royal.admin.modular.api.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 题库控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

    private String PREFIX = "/modular/business/topic/";

    @Autowired
    private TopicService topicService;

    /**
     * 跳转到题库列表首页
     *
     * @author royal
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 获取题库列表
     *
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Page<Map<String, Object>> list = topicService.selectPageList();
        return LayuiPageFactory.createPageInfo(list);
    }

    /**
     * 获取题库详情
     *
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public ResponseData get(Integer id) {
        TopicModel topicModel = topicService.getById(id);
        Map<String, Object> topicMap = BeanUtil.beanToMap(topicModel);
        return ResponseData.success(topicMap);
    }


    /**
     * 跳转到添加题目
     *
     * @author royal
     */
    @RequestMapping("/toAdd")
    public String noticeAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 新增题目
     *
     * @author royal
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TopicModel topicModel) {
        if (ToolUtil.isOneEmpty(topicModel.getTitle(), topicModel.getAnswer(), topicModel.getOptions())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        String[] optionsArr = topicModel.getOptions().split(";");
        for (String options : optionsArr) {
            if (options.equals(topicModel.getAnswer())) {
                this.topicService.save(topicModel);
                return SUCCESS_TIP;
            }
        }
        throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
    }

    /**
     * 跳转到编辑题目
     *
     * @author royal
     */
    @RequestMapping("/toEdit")
    public String noticeEdit() {
        return PREFIX + "edit.html";
    }

    /**
     * 编辑题目
     *
     * @author royal
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(TopicModel topicModel) {
        if (ToolUtil.isOneEmpty(topicModel.getTitle(), topicModel.getAnswer(), topicModel.getOptions())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if (topicModel.getOptions().indexOf(topicModel.getAnswer()) < 0) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.topicService.updateById(topicModel);
        return SUCCESS_TIP;
    }

    /**
     * 导出表格
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        String[] columnNames = {"题目", "选项", "答案"};
        String fileName = "题库";
        ExportExcelUtil<TopicJson> util = new ExportExcelUtil<TopicJson>();
        TopicJson topicJson = new TopicJson();
        topicJson.setAnswer("黄鹤楼");
        topicJson.setOptions("黄鹤楼;楼外楼;岳阳楼");
        topicJson.setTitle("下列哪个建筑属于武汉？");
        List<TopicJson> list = new ArrayList<>();
        list.add(topicJson);
        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    /**
     * 跳转到导入题目
     *
     * @author royal
     */
    @RequestMapping("/toImport")
    public String toImport() {
        return PREFIX + "import.html";
    }

    /**
     * 导入题库
     *
     * @return
     */
    @RequestMapping(value = "/readExcel")
    @ResponseBody
    public Object readExcel(HttpServletRequest request,String qId,String wId) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        // 取得request中的所有文件名
        Iterator<String> iter = multiRequest.getFileNames();
        StringBuffer sb = new StringBuffer();
        while (iter.hasNext()) {
            // 取得上传文件
            MultipartFile file = multiRequest.getFile(iter.next());
            if (!file.isEmpty()) {
                List<TopicModel> lnydTsInfoList = ObjectExcelRead.batchTopicJson(file);
                if(lnydTsInfoList!=null&&lnydTsInfoList.size()>0) {
                    for (TopicModel topicModel : lnydTsInfoList) {
                        topicModel.setWId(wId);
                        topicModel.setQId(qId);
                    }
                    this.topicService.saveBatch(lnydTsInfoList);
                }
            }
        }
        return SUCCESS_TIP;
    }


    /**
     * 删除题目
     *
     * @author royal
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String id) {
        this.topicService.removeById(id);
        return SUCCESS_TIP;
    }


}
