package cn.andzhang.android.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;
import cn.andzhang.android.model.config.DingDingNewsBean;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/28 18:44
 * @description  钉钉
 */
public interface DingDingApiService {

    /**
     * 发送自定义消息形式到钉钉
     */
    @POST
    Call<Object> sendApkToDingDing(@Url String url, @Body DingDingNewsBean body);

}