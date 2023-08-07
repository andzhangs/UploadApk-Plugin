package cn.andzhang.android.manager;

import java.io.IOException;

import cn.andzhang.android.api.DingDingApiService;
import cn.andzhang.android.model.config.DingDingNewsBean;
import cn.andzhang.android.util.Logger;
import retrofit2.Response;

/**
 * 钉钉操作类
 * @author zhangshuai
 */
public class DingDingManager {

    private static volatile DingDingManager sInstance = null;

    private final DingDingApiService dingDingApiService;
    private final String mWebHookUrl;

    private DingDingManager(DingDingApiService apiService, String webHookUrl) {
        this.dingDingApiService = apiService;
        this.mWebHookUrl = webHookUrl;
    }

    public static void init(DingDingApiService apiService, String webHookUrl) {
        if (sInstance == null) {
            synchronized (DingDingManager.class) {
                sInstance = new DingDingManager(apiService, webHookUrl);
            }
        }
    }

    public static DingDingManager getInstance() {
        return sInstance;
    }

    /**
     * 发送文本到钉钉
     */
    public void sendApkToDingDing(String from, DingDingNewsBean content) {
        try {
            Response<Object> response = dingDingApiService.sendApkToDingDing(mWebHookUrl, content).execute();
            Object result = response.body();
            Logger.print(from + " 发布成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
