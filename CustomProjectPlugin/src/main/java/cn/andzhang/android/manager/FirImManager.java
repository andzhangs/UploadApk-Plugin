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
 */
public class FirImManager {

    private PluginConfigBean mData;
    private FirApiService firApiService;
    private FirImTokenBean mFirImTokenBean;

    private static volatile FirImManager sInstance = null;

    private FirImManager(PluginConfigBean data, FirApiService apiService) {
        Logger.print("初始化Fir.Im");
        this.mData = data;
        this.firApiService = apiService;
    }

    public static void init(PluginConfigBean data, FirApiService apiService) {
        if (sInstance == null) {
            synchronized (FirImManager.class) {
                sInstance = new FirImManager(data, apiService);
            }
        }
    }

    public static FirImManager getInstance() {
        return sInstance;
    }


    /**
     * 获取Fir.im的token
     */
    public void getFirImToken() {
        try {
            Map<String, String> map = new HashMap<String, String>(3) {
                {
                    put("type", mData.firImConfig.type);
                    put("bundle_id", mData.firImConfig.packageName);
                    put("api_token", mData.firImConfig.apiToken);
                }
            };

            Call<FirImTokenBean> call = firApiService.getFirImToken(map);
            Response<FirImTokenBean> response = call.execute();
            FirImTokenBean result = response.body();
            if (result != null) {
                mFirImTokenBean = result;
                Logger.print("FirIm Token："+result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传apk和icon文件到Firm.im
     */
    public void uploadApkToFirIm() {
        try {
            if (mFirImTokenBean == null) {
                return;
            }
            if (mData.apkOutputPath != null && mData.apkOutputPath.length() > 0) {
                File apkFile = new File(mData.apkOutputPath);

                if (apkFile.exists()) {
                    RequestBody apkFileBody = RequestBody.create(apkFile, MediaType.parse("multipart/form-data"));
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);
                    builder.addFormDataPart("key", mFirImTokenBean.cert.binary.key);
                    builder.addFormDataPart("token", mFirImTokenBean.cert.binary.token);
                    builder.addFormDataPart("x:name", mData.firImConfig.xName);
                    builder.addFormDataPart("x:version", mData.firImConfig.xVersion);
                    builder.addFormDataPart("x:build", mData.firImConfig.xBuild);
                    builder.addFormDataPart("x:changelog", mData.firImConfig.xChangeLog);
                    builder.addFormDataPart("file", apkFile.getName(), apkFileBody);
                    List<MultipartBody.Part> parts = builder.build().parts();
                    Call<FirImUploadBean> call = firApiService.uploadToFirIm(mFirImTokenBean.cert.binary.upload_url, parts);

                    Response<FirImUploadBean> response = call.execute();
                    FirImUploadBean result = response.body();

                    if (result == null) {
                        return;
                    }
                    String apkStatue = result.is_completed ? "成功" : "失败！！！";
                    Logger.print(">>>>>>>>>> apk上传：" + apkStatue);

                    if (result.is_completed) {

                        getApplicationList();

                        if (mData.firImConfig.icon != null && mData.firImConfig.icon.length() > 0) {
                            File iconFile = new File(mData.firImConfig.icon);
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.print("icon图标上传：失败！！！");
        }
    }

    /**
     * 查看应用列表
     */
    private void getApplicationList() {
        try {
            Call<FirApplicationBean> call = firApiService.getApplicationList(mData.firImConfig.apiToken);
            Response<FirApplicationBean> response = call.execute();
            FirApplicationBean beans = response.body();
            for (int i = 0; i < Objects.requireNonNull(beans).getItems().size(); i++) {
                FirApplicationBean.ItemsBean bean = beans.getItems().get(i);
                Logger.print("应用：" + bean.getName() + "--->" + bean);
                if (bean.getName().equals(mData.firImConfig.xName) && bean.getBundle_id().equals(mData.firImConfig.packageName)) {
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
     *
     * @param appId
     */
    private void getApkInfo(String appId) {
        try {
            Call<FirApkDetailBean> result = firApiService.getApkDetailInfo(appId, mData.firImConfig.apiToken);
            Response<FirApkDetailBean> response = result.execute();
            FirApkDetailBean data = response.body();
            Logger.print("应用详情：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToDDing(FirApplicationBean.ItemsBean bean) {
        //通知类型
        String dingDingType = mData.ddContent.msgtype;
        if (!dingDingType.isEmpty() && dingDingType != null ) {
            if (dingDingType.equals(DingDingContentType.LINK.getType())) {
                if (mData.ddContent.link.messageUrl.isEmpty() || mData.ddContent.link.messageUrl == null) {
                    mData.ddContent.link.messageUrl = mData.firImConfig.bindingHost + bean.getShortX();
                }
            } else if (dingDingType.equals(DingDingContentType.ACTION_CARD.getType())) {
                if (mData.ddContent.actionCard.singleURL.isEmpty()) {
                    mData.ddContent.actionCard.singleURL = mData.firImConfig.bindingHost + bean.getShortX();
                }
            } else if (dingDingType.equals(DingDingContentType.FEED_CARD.getType())) {
                for (DingDingNewsBean.FeedCardBean.FeedCardLinksBean link : mData.ddContent.feedCard.links) {
                    if (link.messageURL.isEmpty()) {
                        link.messageURL = mData.firImConfig.bindingHost + bean.getShortX();
                    }
                }
            }
            Logger.print("发送消息到钉钉："+mData.ddContent);
            DingDingManager.getInstance().sendApkToDingDing(mData.ddContent);

        }else{
            Logger.print("为设置钉钉通知类型: msgtype 字段");
        }
    }

}
