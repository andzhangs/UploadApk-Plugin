package cn.andzhang.android.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.andzhang.android.api.PgyApiService;
import cn.andzhang.android.model.config.PluginConfigBean;
import cn.andzhang.android.model.response.BaseResponseBean;
import cn.andzhang.android.model.response.pgy.PgyAppDetailInfoBean;
import cn.andzhang.android.model.response.pgy.PgyTokenBean;
import cn.andzhang.android.util.Logger;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 蒲公英操作类
 * 官方文档：https://www.pgyer.com/doc/view/api#commonParams
 */
public class PgyManager {

    private static volatile PgyManager sInstance = null;
    private final PgyApiService mPgyApiService;
    private final PluginConfigBean mConfigBean;
    private PgyTokenBean mUploadToken;

    private PgyManager(PgyApiService pgyApiService, PluginConfigBean bean) {
        this.mPgyApiService = pgyApiService;
        this.mConfigBean = bean;
    }

    public static void init(PgyApiService pgyApiService, PluginConfigBean bean) {
        if (sInstance == null) {
            synchronized (PgyManager.class) {
                sInstance = new PgyManager(pgyApiService, bean);
            }
        }
    }

    public static PgyManager getInstance() {
        return sInstance;
    }

    /**
     * 获取蒲公英token
     */
    public void getPgyToken() {
        try {
            Map<String, Object> map = new HashMap<>(11) {
                {
                    put("_api_key", mConfigBean.pgyConfig.pgyApiKey);
                    put("buildType", mConfigBean.pgyConfig.pgyBuildType);
                    put("oversea", mConfigBean.pgyConfig.pgyOversea);
                    put("buildInstallType", mConfigBean.pgyConfig.pgyBuildInstallType);
                    put("buildPassword", mConfigBean.pgyConfig.pgyBuildPassword);
                    put("buildDescription", mConfigBean.pgyConfig.pgyBuildDescription);
                    put("buildUpdateDescription", mConfigBean.pgyConfig.pgyBuildUpdateDescription);
                    put("buildInstallDate", mConfigBean.pgyConfig.pgyBuildInstallDate);
                    put("buildInstallStartDate", mConfigBean.pgyConfig.pgyBuildInstallStartDate);
                    put("buildInstallEndDate", mConfigBean.pgyConfig.pgyBuildInstallEndDate);
                    put("buildChannelShortcut", mConfigBean.pgyConfig.pgyBuildChannelShortcut);
                }
            };
            Call<BaseResponseBean<PgyTokenBean>> callBack = mPgyApiService.getPgyToken(map);
            Response<BaseResponseBean<PgyTokenBean>> result = callBack.execute();
            Logger.print(">>>>>>>>>> HttpRequest.getUploadKey：" + result.body());
            if (result.body() != null) {
                mUploadToken = result.body().data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传apk文件到蒲公英
     */
    public void uploadApkToPgy() {
        Logger.print("运行了uploadApk：" + mUploadToken);
        if (mUploadToken != null && mConfigBean.apkOutputPath != null && mConfigBean.apkOutputPath.length() > 0) {
            try {
                File file = new File(mConfigBean.apkOutputPath);
                if (file.exists()) {
                    RequestBody fileBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);
                    builder.addFormDataPart("key", mUploadToken.key);
                    builder.addFormDataPart("signature", mUploadToken.params.signature);
                    builder.addFormDataPart("x-cos-security-token", mUploadToken.params.xToken);
                    builder.addFormDataPart("x-cos-meta-file-name", mConfigBean.pgyConfig.apkName);
                    builder.addFormDataPart("file", file.getName(), fileBody);
                    List<MultipartBody.Part> parts = builder.build().parts();

                    Call<BaseResponseBean<Object>> callBack = mPgyApiService.uploadApkToPgy(mUploadToken.endpoint, parts);
                    Response<BaseResponseBean<Object>> result = callBack.execute();
                    Logger.print(">>>>>>>>>> HttpRequest.uploadApk：http状态码：" + result.code() +"状态：" + result.isSuccessful() +"返回数据："+ result.body());
                    if (204 == result.code()) {
                        getCurrentReleaseFinishedAppInfo();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发版后获取蒲公英版本信息
     * 错误码，1216 应用发布失败
     * 错误码，1247 应用正在发布中
     * 返回 code = 1246 ，可间隔 3s ~ 5s 重新调用 URL 进行检测，直到返回成功或失败。
     */
    public void getCurrentReleaseFinishedAppInfo(){

    }


    public void getAppDetailInfo() {
        try {
            Map<String, Object> map = new HashMap<>(3) {
                {
                    put("_api_key", mConfigBean.pgyConfig.pgyApiKey);
                    put("appKey", mConfigBean.pgyConfig.pgyAppKey);
                    put("buildKey", mUploadToken.key); //Build Key是唯一标识应用的索引ID，可以通过 获取App所有版本取得

                }
            };
            Call<BaseResponseBean<PgyAppDetailInfoBean>> callBack = mPgyApiService.getAppDetailInfo(map);
            Response<BaseResponseBean<PgyAppDetailInfoBean>> result = callBack.execute();
            if (result.body() != null && result.body().data != null) {
                Logger.print(">>>>>>>>>> HttpRequest.getAppDetailInfo：" + result.body().data);
                if (result.isSuccessful()) {
                    DingDingManager.getInstance().sendApkToDingDing(mConfigBean.ddContent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
