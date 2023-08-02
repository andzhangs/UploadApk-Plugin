package cn.andzhang.android.http;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.apache.commons.codec.binary.Base64;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.andzhang.android.api.FirApiService;
import cn.andzhang.android.api.PgyApiService;
import cn.andzhang.android.manager.DingDingManager;
import cn.andzhang.android.manager.FirImManager;
import cn.andzhang.android.manager.PgyManager;
import cn.andzhang.android.util.Logger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import cn.andzhang.android.api.DingDingApiService;
import cn.andzhang.android.model.config.PluginConfigBean;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/28 18:45
 * @description
 */
public class HttpRequest {

    private static Project mProject;
    public PluginConfigBean mConfigBean;
    private PgyManager mPgyManager;
    private FirImManager mFirImManager;

    public HttpRequest(Project project) {
        File file = new File(project.getRootDir().getAbsolutePath() + "/upload-apk.json");
        if (!file.exists()) {
            return;
        }

        mProject = project;
        Gson mGson = new Gson();
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(file));
            createSign(mGson.fromJson(jsonReader, PluginConfigBean.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全配置
     */
    private void createSign(PluginConfigBean data) {
        try {
            Long mTimestamp = System.currentTimeMillis();
            String stringToSign = mTimestamp + "\n" + data.ddConfig.ddWebSecret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(data.ddConfig.ddWebSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
            data.ddConfig.ddWebHookUrl += "&timestamp=" + mTimestamp + "&sign=" + sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mConfigBean = data;

        loadHttp();
    }

    private void loadHttp() {
        File apkFile = new File(mConfigBean.apkOutputPath);
        if (!apkFile.exists()) {
            Logger.print("Apk安装包不存在");
            return;
        }

        HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor(s -> {
//            Logger.print("输出日志：" + s);
            if (s.contains("<-- 201")) {
                Logger.print("Fir.im请求成功！！");
            }

            if (s.contains("<-- 204")) {
                Logger.print("蒲公英上传成功！！");
            }
        });
        mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkhttpClient = new OkHttpClient.Builder().addInterceptor(mInterceptor).build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(mConfigBean.isPgy ? mConfigBean.pgyConfig.pgyApiUrl : mConfigBean.firImConfig.firApiUrl)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DingDingApiService dingDingApiService = mRetrofit.create(DingDingApiService.class);
        DingDingManager.init(dingDingApiService, mConfigBean.ddConfig.ddWebHookUrl);

        if (mConfigBean.isPgy) {
            PgyApiService pgyApiService = mRetrofit.create(PgyApiService.class);
            mPgyManager = new PgyManager(pgyApiService, mConfigBean);
        } else {
            //初始化FirIm
            FirApiService firApiService = mRetrofit.create(FirApiService.class);
            mFirImManager = new FirImManager(mConfigBean, firApiService);
        }
    }


    /**
     * 获取token
     */
    public void getToken() {
//        Logger.print("获取平台Token...");
        if (mConfigBean.isPgy) {
            if (mConfigBean.pgyConfig != null) {
                mPgyManager.getPgyToken();
            } else {
                Logger.print("请配置蒲公英相关数据！！！");
            }
        } else {
            if (mConfigBean.firImConfig != null) {
                mFirImManager.getFirImToken();
            } else {
                Logger.print("请配置Fir.im相关数据！！！");
            }
        }
    }

    /**
     * 上传apk文件
     */
    public void uploadApk() {
        Logger.print("开始上传Apk...");
        if (mConfigBean.isPgy) {
            if (mConfigBean.pgyConfig != null) {
                mPgyManager.uploadApkToPgy();
            } else {
                Logger.print("请配置蒲公英相关数据！！！");
            }
        } else {
            if (mConfigBean.firImConfig != null) {
                mFirImManager.uploadApkToFirIm();
            } else {
                Logger.print("请配置Fir.im相关数据！！！");
            }
        }
    }
}