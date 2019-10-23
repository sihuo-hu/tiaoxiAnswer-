package com.royal.config;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Component
public class QiNiuCloudConfig {
    private static final Logger log = LoggerFactory.getLogger (QiNiuCloudConfig.class);
    private static final String QINIU_ACCESS_KEY = "1OZ8XL8ay3mDkBVbdg6bI3x41cvQca3l0vS4lMzS";
    private static final String QINIU_SECRET_KEY = "kRxBw4hrs5CQpkIQUiIEqDfbDGMiAJQHZ-KUoqlU";
    private static final String QINIU_BUCKET = "zst-img";
    private static final String QINIU_DOMAIN = "http://img.zhangstz.com/";

    /**
     * @param file 上传文件
     * @param key  文件夹/文件名
     * @return
     * @throws Exception
     */
    public String uploadQiNinCloud(InputStream file, String key) throws Exception {
        //开始上传
        Configuration cfg = new Configuration (Zone.zone2 ());
        UploadManager uploadManager = new UploadManager (cfg);
        Auth auth = Auth.create (QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
        //生成token
        String upToken = auth.uploadToken (QINIU_BUCKET, key);

        try {
            uploadManager.put (file, key, upToken, null, null);
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        return QINIU_DOMAIN + key;
    }

}
