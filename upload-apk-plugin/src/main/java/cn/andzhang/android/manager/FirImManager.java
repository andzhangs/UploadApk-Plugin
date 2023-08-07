package cn.andzhang.android.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import cn.andzhang.android.api.FirApiService;
import cn.andzhang.android.model.config.DingDingContentType;
import cn.andzhang.android.model.config.DingDingNewsBean;
import cn.andzhang.android.model.config.PluginConfigBean;
import cn.andzhang.android.model.response.fir.FirApkDetailBean;
import cn.andzhang.android.model.response.fir.FirApplicationBean;
import cn.andzhang.android.model.response.fir.FirImTokenBean;
import cn.andzhang.android.model.response.fir.FirImUploadBean;
import cn.andzhang.android.util.Logger;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * FirIm操作类
 *
 * @author zhangshuai
 */
public class FirImManager {

    private final PluginConfigBean mConfigBean;
    private final FirApiService firApiService;
    private FirImTokenBean mFirImTokenBean;

    public FirImManager(PluginConfigBean data, FirApiService apiService) {
        this.mConfigBean = data;
        this.firApiService = apiService;
    }

    /**
     * 获取Fir.im的token
     */
    public void getFirImToken() {
        try {
            Map<String, String> map = new HashMap<>(3) {
                {
                    put("type", mConfigBean.firImConfig.type);
                    put("bundle_id", mConfigBean.firImConfig.packageName);
                    put("api_token", mConfigBean.firImConfig.apiToken);
                }
            };

            Call<FirImTokenBean> call = firApiService.getFirImToken(map);
            Response<FirImTokenBean> response = call.execute();
            FirImTokenBean result = response.body();
            if (result != null) {
                mFirImTokenBean = result;
                Logger.print("FirIm Token：" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传apk和icon文件到Firm.im
     */
    public void uploadApkToFirIm(File apkOutputPath) {
        try {
            if (mFirImTokenBean == null) {
                return;
            }
            RequestBody apkFileBody = RequestBody.create(apkOutputPath, MediaType.parse("multipart/form-data"));
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("key", mFirImTokenBean.cert.binary.key);
            builder.addFormDataPart("token", mFirImTokenBean.cert.binary.token);
            builder.addFormDataPart("x:name", mConfigBean.firImConfig.xName);
            builder.addFormDataPart("x:version", mConfigBean.firImConfig.xVersion);
            builder.addFormDataPart("x:build", mConfigBean.firImConfig.xBuild);
            builder.addFormDataPart("x:changelog", mConfigBean.firImConfig.xChangeLog);
            builder.addFormDataPart("file", apkOutputPath.getName(), apkFileBody);
            List<MultipartBody.Part> parts = builder.build().parts();
            Call<FirImUploadBean> call = firApiService.uploadToFirIm(mFirImTokenBean.cert.binary.upload_url, parts);

            Response<FirImUploadBean> response = call.execute();
            FirImUploadBean result = response.body();

            if (result == null) {
                return;
            }

            if (result.is_completed) {

                getApplicationList();

                if (mConfigBean.firImConfig.icon != null && mConfigBean.firImConfig.icon.length() > 0) {
                    File iconFile = new File(mConfigBean.firImConfig.icon);
                    if (iconFile.exists()) {
                        RequestBody iconFileBody = RequestBody.create(iconFile, MediaType.parse("image/*"));

                        MultipartBody.Builder iconBuilder = new MultipartBody.Builder();
                        iconBuilder.setType(MultipartBody.FORM);
                        iconBuilder.addFormDataPart("key", mFirImTokenBean.cert.icon.key);
                        iconBuilder.addFormDataPart("token", mFirImTokenBean.cert.icon.token);
                        iconBuilder.addFormDataPart("file", iconFile.getName(), iconFileBody);
                        List<MultipartBody.Part> iconParts = iconBuilder.build().parts();

                        Call<FirImUploadBean> iconCall = firApiService.uploadToFirIm(mFirImTokenBean.cert.icon.upload_url, iconParts);
                        Response<FirImUploadBean> iconResponse = iconCall.execute();
                        FirImUploadBean firImUploadBean = iconResponse.body();
                        if (firImUploadBean != null) {
                            String iconStatue = firImUploadBean.is_completed ? "成功" : "失败！！！";
                            Logger.print("icon图标上传：" + iconStatue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.print("icon图标上传：失败。" + e);
        }
    }

    /**
     * 查看应用列表
     */
    private void getApplicationList() {
        try {
            Call<FirApplicationBean> call = firApiService.getApplicationList(mConfigBean.firImConfig.apiToken);
            Response<FirApplicationBean> response = call.execute();
            FirApplicationBean beans = response.body();
            for (int i = 0; i < Objects.requireNonNull(beans).getItems().size(); i++) {
                FirApplicationBean.ItemsBean bean = beans.getItems().get(i);
                if (bean.getName().equals(mConfigBean.firImConfig.xName) && bean.getBundle_id().equals(mConfigBean.firImConfig.packageName)) {
                    sendToDDing(bean);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取应用信息
     */
    private void getApkInfo(String appId) {
        try {
            Call<FirApkDetailBean> result = firApiService.getApkDetailInfo(appId, mConfigBean.firImConfig.apiToken);
            Response<FirApkDetailBean> response = result.execute();
            FirApkDetailBean data = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToDDing(FirApplicationBean.ItemsBean bean) {
        //通知类型
        String dingDingType = mConfigBean.ddContent.msgtype;
        if (dingDingType != null && !dingDingType.isEmpty()) {
            if (dingDingType.equals(DingDingContentType.LINK.getType())) {
                if (mConfigBean.ddContent.link.messageUrl == null || mConfigBean.ddContent.link.messageUrl.isEmpty()) {
                    mConfigBean.ddContent.link.messageUrl = mConfigBean.firImConfig.bindingHost + bean.getShortX();
                }
            } else if (dingDingType.equals(DingDingContentType.ACTION_CARD.getType())) {
                if (mConfigBean.ddContent.actionCard.singleURL.isEmpty()) {
                    mConfigBean.ddContent.actionCard.singleURL = mConfigBean.firImConfig.bindingHost + bean.getShortX();
                }
            } else if (dingDingType.equals(DingDingContentType.FEED_CARD.getType())) {
                for (DingDingNewsBean.FeedCardBean.FeedCardLinksBean link : mConfigBean.ddContent.feedCard.links) {
                    if (link.messageURL.isEmpty()) {
                        link.messageURL = mConfigBean.firImConfig.bindingHost + bean.getShortX();
                    }
                }
            }
            DingDingManager.getInstance().sendApkToDingDing("Fir.im", mConfigBean.ddContent);
        } else {
            Logger.print("未设置钉钉通知类型: msgtype 字段");
        }
    }
}
