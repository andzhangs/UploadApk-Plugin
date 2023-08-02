package cn.andzhang.android.manager;

import java.io.IOException;
import cn.andzhang.android.api.DingDingApiService;
import cn.andzhang.android.model.config.DingDingNewsBean;
import retrofit2.Response;

/**
 * 钉钉操作类
 */
public class DingDingManager {

    private static volatile DingDingManager sInstance = null;

    private DingDingApiService dingDingApiService;
    private String mWebHookUrl;

    private DingDingManager(DingDingApiService apiService, String webHookUrl) {
        this.dingDingApiService = apiService;
        this.mWebHookUrl = webHookUrl;
    }

    public static void init(DingDingApiService apiService,String webHookUrl) {
        if (sInstance == null) {
            synchronized (DingDingManager.class) {
                sInstance = new DingDingManager(apiService,webHookUrl);
            }
        }
    }

    public static DingDingManager getInstance() {
        return sInstance;
    }

    /**
     * 发送文本到钉钉
     */
    public void sendApkToDingDing(DingDingNewsBean content) {
        try {
            Response<Object> response = dingDingApiService.sendApkToDingDing(mWebHookUrl, content).execute();
            Object result = response.body();
//            Logger.print(">>>>>>>>>> HttpRequest.SendApkToDingDing：" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
