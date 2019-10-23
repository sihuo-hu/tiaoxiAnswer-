package com.royal.commen.rong;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.royal.commen.rong.models.RYIDResponse;
import com.royal.util.Tools;
import net.sf.json.JSONObject;

import com.royal.commen.rong.models.FormatType;
import com.royal.commen.rong.models.InfoNtfMessage;
import com.royal.commen.rong.models.SdkHttpResult;
import com.royal.commen.rong.util.ApiHttpClient;

public class RYUtil {
    private static String APP_KEY = "p5tvi9dspeel4";
    private static String APP_SECRET = "uYwK8bEJzs7klG";


    /**
     * 获取用户融云即时通讯ID
     *
     * @return 通讯ID
     * @throws Exception
     */
    public static String getRY_ID(RYIDResponse data) throws Exception {
        String value = "";
        value = ApiHttpClient.getToken (APP_KEY, APP_SECRET, data.getUserId (), data.getUserName (), data.getImg (),
                FormatType.json).getResult ();
        if (Tools.notEmpty (value)) {
            JSONObject jsonObject = JSONObject.fromObject (value);
            System.out.println (jsonObject.toString ());
            value = jsonObject.getString ("token");
        }
        return value;
    }

    /**
     * 获取用户融云即时通讯IDList
     *
     * @param dataList List<RYIDResponse>
     * @return Map<String   ,   String> userId,ryId
     * @throws Exception
     */
    public static Map<String, String> getRY_IDList(List<RYIDResponse> dataList) throws Exception {
        Map<String, String> map = new HashMap<String, String> ();
        for (RYIDResponse ryidResponse : dataList) {
            String ryId = getRY_ID (ryidResponse);
            map.put (ryidResponse.getUserId (), ryId);
        }
        return map;
    }

    /**
     * 更新用户信息
     *
     * @param userId
     * @param userName
     * @param portraitUri
     * @return
     * @throws Exception
     */
    public static SdkHttpResult refreshUser(String userId, String userName, String portraitUri) throws Exception {
        return ApiHttpClient.refreshUser (APP_KEY, APP_SECRET, userId, userName, portraitUri, FormatType.json);
    }

    /**
     * 创建或加入群
     *
     * @param userIds   用户 TS_ID
     * @param groupId   班级ID
     * @param groupName 班级名称
     * @throws Exception 访问出错时抛出异常
     */
    public static void createGroup(List<String> userIds, String groupId, String groupName) throws Exception {
        System.out
                .println (ApiHttpClient.createGroup (APP_KEY, APP_SECRET, userIds, groupId, groupName, FormatType.json));
    }

    /**
     * 退出群
     *
     * @throws Exception
     */
    public static void quitGroupBatch(List<String> userIds, String groupId) throws Exception {
        ApiHttpClient.quitGroupBatch (APP_KEY, APP_SECRET, userIds, groupId, FormatType.json);
    }

    /**
     * 解散群
     *
     * @throws Exception
     */
    public static void dismissGroup(String ts_id, String groupId) throws Exception {
        ApiHttpClient.dismissGroup (APP_KEY, APP_SECRET, ts_id, groupId, FormatType.json);
    }

    /**
     * 修改群名称
     */
    public static void refreshGroupInfo(String groupId, String groupName) throws Exception {
        ApiHttpClient.refreshGroupInfo (APP_KEY, APP_SECRET, groupId, groupName, FormatType.json);
    }

    /**
     * 获取消息历史下载地址
     */
    public static SdkHttpResult getMessageHistoryUrl(String date) throws Exception {
        return ApiHttpClient.getMessageHistoryUrl (APP_KEY, APP_SECRET, date, FormatType.json);
    }

    /**
     * 发送群组小灰条消息
     *
     * @param fromUserId
     * @param list
     * @param infoNtfMessage
     * @throws Exception
     */
    public static void InfoNtfMessage(String fromUserId, List<String> list, InfoNtfMessage infoNtfMessage)
            throws Exception {
        ApiHttpClient.publishGroupMessage (APP_KEY, APP_SECRET, fromUserId, list, infoNtfMessage, null, null,
                FormatType.json);
    }

    /**
     * 发送群组小灰条消息
     *
     * @param fromUserId
     * @param list
     * @param infoNtfMessage
     * @throws Exception
     */
    public static void InfoNtfMessage(String fromUserId, String groupId, InfoNtfMessage infoNtfMessage)
            throws Exception {
        List<String> list = new ArrayList<String> ();
        list.add (groupId);
        ApiHttpClient.publishGroupMessage (APP_KEY, APP_SECRET, fromUserId, list, infoNtfMessage, null, null,
                FormatType.json);
    }


}
