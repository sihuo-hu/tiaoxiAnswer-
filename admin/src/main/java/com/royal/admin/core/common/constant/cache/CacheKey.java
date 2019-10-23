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
package com.royal.admin.core.common.constant.cache;

/**
 * 缓存标识前缀集合,常用在ConstantFactory类中
 *
 * @author fengshuonan
 * @date 2017-04-25 9:37
 */
public interface CacheKey {

    /**
     * 角色名称(多个)
     */
    String ROLES_NAME = "roles_name_";

    /**
     * 角色名称(单个)
     */
    String SINGLE_ROLE_NAME = "single_role_name_";

    /**
     * 角色英文名称
     */
    String SINGLE_ROLE_TIP = "single_role_tip_";

    /**
     * 部门名称
     */
    String DEPT_NAME = "dept_name_";

    /**
     * 活动章节题目信息
     */
    String QUESTION_PAPER = "question_paper_";
    /**
     * 最近的活动
     */
    String QUESTION_PAPER_NEAREST = "question_paper_nearest";

    /**
     * 活动下每个章节的题目数
     */
    String QUESTION_PAPER_WRITINGS_COUNT = "question_paper_writings_count_";

    /**
     * 章节详情
     */
    String WRITINGS_INFO = "writings_info_";

    /**
     * 章节完成度
     */
    String WRITINGS_RIGHT = "writings_right";
    /**
     * 获胜方
     */
    String WINNER = "winner";


    /**
     * 阵营用户章节答对数
     */
    String RIGHT = "right_";
    /**
     * 阵营章节全对数
     */
    String ALL_RIGHT = "all_right_";
    String ALL = "all_";
    String USER_COUNT = "user_count_";

}
