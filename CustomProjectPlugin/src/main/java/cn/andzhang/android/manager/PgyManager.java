package cn.andzhang.android.manager;

/**
 * 蒲公英操作类
 */
public class PgyManager {
    
    private static volatile PgyManager sInstance = null;
    
    private PgyManager() {}
    
    public static void init() {
        if (sInstance == null) {
            synchronized (PgyManager.class) {
                sInstance = new PgyManager();
            }
        }
    }
    
    public static PgyManager getInstance() {
        return sInstance;
    }

//    /**
//     * 获取蒲公英token
//     */
//    private void getPgyToken() {
//        try {
//            Map<String, Object> map = new HashMap<String, Object>(11) {
//                {
//                    put("_api_key", mData.pgyConfig.pgyApiKey);
//                    put("buildType", mData.pgyConfig.pgyBuildType);
//                    put("oversea", mData.pgyConfig.pgyOversea);
//                    put("buildInstallType", mData.pgyConfig.pgyBuildInstallType);
//                    put("buildPassword", mData.pgyConfig.pgyBuildPassword);
//                    put("buildDescription", mData.pgyConfig.pgyBuildDescription);
//                    put("buildUpdateDescription", mData.pgyConfig.pgyBuildUpdateDescription);
//                    put("buildInstallDate", mData.pgyConfig.pgyBuildInstallDate);
//                    put("buildInstallStartDate", mData.pgyConfig.pgyBuildInstallStartDate);
//                    put("buildInstallEndDate", mData.pgyConfig.pgyBuildInstallEndDate);
//                    put("buildChannelShortcut", mData.pgyConfig.pgyBuildChannelShortcut);
//                }
//            };
//            Call<BaseResponseBean<PgyTokenBean>> callBack = mApiService.getPgyToken(map);
//            Response<BaseResponseBean<PgyTokenBean>> result = callBack.execute();
//            Logger.print(">>>>>>>>>> HttpRequest.getUploadKey：" + result.body());
//            if (result.body() != null) {
//                mUploadToken = result.body().data;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 上传apk文件到蒲公英
//     */
//    private void uploadApkToPgy() {
//        Logger.print("运行了uploadApk：" + mUploadToken);
//        if (mUploadToken != null && mData.apkOutputPath != null && mData.apkOutputPath.length() > 0) {
//            try {
//                File file = new File(mData.apkOutputPath);
//                if (file.exists()) {
//                    RequestBody fileBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
//                    MultipartBody.Builder builder = new MultipartBody.Builder();
//                    builder.setType(MultipartBody.FORM);
//                    builder.addFormDataPart("key", mUploadToken.key);
//                    builder.addFormDataPart("signature", mUploadToken.params.signature);
//                    builder.addFormDataPart("x-cos-security-token", mUploadToken.params.xToken);
//                    builder.addFormDataPart("x-cos-meta-file-name", mData.apkName);
//                    builder.addFormDataPart("file", file.getName(), fileBody);
//                    List<MultipartBody.Part> parts = builder.build().parts();
//
//                    Call<BaseResponseBean<Object>> callBack = mApiService.uploadApkToPgy(mUploadToken.endpoint, parts);
//                    Response<BaseResponseBean<Object>> result = callBack.execute();
//                    Logger.print(">>>>>>>>>> HttpRequest.uploadApk：" + result.body());
//                    if (isUpload) {
//                        getAppDetailInfo();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 发版后获取蒲公英版本信息
//     */
//    public void getAppDetailInfo() {
//        try {
//            Map<String, Object> map = new HashMap<String, Object>(3) {
//                {
//                    put("_api_key", mData.pgyConfig.pgyApiKey);
//                    put("appKey", mData.pgyConfig.pgyAppKey);
//                    put("buildKey", ""); //Build Key是唯一标识应用的索引ID，可以通过 获取App所有版本取得
//                }
//            };
//            Call<BaseResponseBean<PgyAppDetailInfoBean>> callBack = mApiService.getAppDetailInfo(map);
//            Response<BaseResponseBean<PgyAppDetailInfoBean>> result = callBack.execute();
//            if (result.body() != null && result.body().data != null) {
//                Logger.print(">>>>>>>>>> HttpRequest.getAppDetailInfo：" + result.body().data);
//                SendApkToDingDing();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
