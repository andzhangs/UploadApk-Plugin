package cn.andzhang.android.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.andzhang.android.Constant;
import cn.andzhang.android.api.PgyApiService;
import cn.andzhang.android.model.config.DingDingContentType;
import cn.andzhang.android.model.config.DingDingNewsBean;
import cn.andzhang.android.model.config.PluginConfigBean;
import cn.andzhang.android.model.response.BaseResponseBean;
import cn.andzhang.android.model.response.pgy.PgyCurrentAppDetailInfoBean;
import cn.andzhang.android.model.response.pgy.PgyTokenBean;
import cn.andzhang.android.util.Logger;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 蒲公英操作类
 * <a href="https://www.pgyer.com/doc/view/api#commonParams">蒲公英官方文档</a>
 * @author zhangshuai
 */
public class PgyManager {

    private final PgyApiService mPgyApiService;
    private final PluginConfigBean mConfigBean;
    private PgyTokenBean mUploadToken;

    public PgyManager(PgyApiService pgyApiService, PluginConfigBean bean) {
        this.mPgyApiService = pgyApiService;
        this.mConfigBean = bean;
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
    public void uploadApkToPgy(File apkOutputPath) {
        if (mUploadToken != null) {
            try {
                RequestBody fileBody = RequestBody.create(apkOutputPath, MediaType.parse("multipart/form-data"));
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                builder.addFormDataPart("key", mUploadToken.key);
                builder.addFormDataPart("signature", mUploadToken.params.signature);
                builder.addFormDataPart("x-cos-security-token", mUploadToken.params.xToken);
                builder.addFormDataPart("x-cos-meta-file-name", mConfigBean.pgyConfig.apkName);
                builder.addFormDataPart("file", apkOutputPath.getName(), fileBody);
                List<MultipartBody.Part> parts = builder.build().parts();

                Call<BaseResponseBean<Object>> callBack = mPgyApiService.uploadApkToPgy(mUploadToken.endpoint, parts);
                Response<BaseResponseBean<Object>> result = callBack.execute();
                if (result.code() == 204) {
                    getCurrentReleaseFinishedAppInfo();
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
    private void getCurrentReleaseFinishedAppInfo() {
        try {
            Map<String, Object> map = new HashMap<>(2) {
                {
                    put("_api_key", mConfigBean.pgyConfig.pgyApiKey);
                    put("buildKey", mUploadToken.key); //Build Key是唯一标识应用的索引ID，可以通过 获取App所有版本取得
                }
            };
            Call<BaseResponseBean<PgyCurrentAppDetailInfoBean>> call = mPgyApiService.getCurrentDetailInfo(map);
            Response<BaseResponseBean<PgyCurrentAppDetailInfoBean>> response = call.execute();
            BaseResponseBean<PgyCurrentAppDetailInfoBean> body = response.body();
            if (body != null) {
                int code = body.code;
                if (code == 1246) {
                    Logger.print("应用发布等待中。。。");
                    Thread.sleep(3000L);
                    getCurrentReleaseFinishedAppInfo();
                } else if (code == 1216) {
                    Logger.print("应用发布失败!!!");
                } else if (code == 1247) {
                    Logger.print("应用正在发布中...");
                    Thread.sleep(3000L);
                    getCurrentReleaseFinishedAppInfo();
                } else if (code == 0) {
                    sendToDDing(body.data);
                } else {
                    Logger.print("蒲公英发布...");
                }
            } else {
                Logger.print("上传失败，网络异常！！！");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Logger.print("获取信息异常：" + e);
        }
    }

    private void sendToDDing(PgyCurrentAppDetailInfoBean bean) {
        DingDingNewsBean dingDingNewsBean = mConfigBean.ddContent;
        String dingDingType = dingDingNewsBean.msgtype;
        if (dingDingType.equals(DingDingContentType.LINK.getType())) {
            if (dingDingNewsBean.link.messageUrl == null || dingDingNewsBean.link.messageUrl.isEmpty()) {
                dingDingNewsBean.link.messageUrl = Constant.DEFAULT_PGY_HOST + bean.buildShortcutUrl;
            }
            if (dingDingNewsBean.link.picUrl == null || dingDingNewsBean.link.picUrl.isEmpty()) {
                dingDingNewsBean.link.picUrl = bean.buildQRCodeURL;
            }
        } else if (dingDingType.equals(DingDingContentType.PHOTO.getType())) {
            if (dingDingNewsBean.photo == null) {
                DingDingNewsBean.PhotoBean photoBean = new DingDingNewsBean.PhotoBean();
                photoBean.photoURL = bean.buildQRCodeURL;
                dingDingNewsBean.photo = photoBean;
            } else if (dingDingNewsBean.photo.photoURL == null || dingDingNewsBean.photo.photoURL.isEmpty()) {
                dingDingNewsBean.link.messageUrl = bean.buildQRCodeURL;
            }
        } else if (dingDingType.equals(DingDingContentType.ACTION_CARD.getType())) {
            dingDingNewsBean.actionCard.text = "![screenshot](" + bean.buildQRCodeURL + ")" + dingDingNewsBean.actionCard.text;
            if (dingDingNewsBean.actionCard.singleURL == null || dingDingNewsBean.actionCard.singleURL.isEmpty()) {
                dingDingNewsBean.actionCard.singleURL = Constant.DEFAULT_PGY_HOST + bean.buildShortcutUrl;
            }
        } else if (dingDingType.equals(DingDingContentType.FEED_CARD.getType())) {
            for (DingDingNewsBean.FeedCardBean.FeedCardLinksBean link : dingDingNewsBean.feedCard.links) {
                if (link.messageURL == null || link.messageURL.isEmpty()) {
                    link.messageURL = Constant.DEFAULT_PGY_HOST + bean.buildShortcutUrl;
                }
                if (link.picURL == null || link.picURL.isEmpty()) {
                    link.picURL = bean.buildQRCodeURL;
                }
            }
        }
        DingDingManager.getInstance().sendApkToDingDing("蒲公英",dingDingNewsBean);
    }
}
