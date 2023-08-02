package cn.andzhang.android.api;

import java.util.List;
import java.util.Map;
import cn.andzhang.android.model.response.BaseResponseBean;
import cn.andzhang.android.model.response.pgy.PgyCurrentAppDetailInfoBean;
import cn.andzhang.android.model.response.pgy.PgyTokenBean;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/28 18:44
 * @description 蒲公英
 */
public interface PgyApiService {

    /**
     * 获取上传token
     */
    @FormUrlEncoded
    @POST("getCOSToken")
    Call<BaseResponseBean<PgyTokenBean>> getPgyToken(@FieldMap Map<String, Object> map);

    /**
     * 上传apk文件到蒲公英
     */
    @Multipart
    @POST
    Call<BaseResponseBean<Object>> uploadApkToPgy(@Url String url,
                                                  @Part List<MultipartBody.Part> parts);

    /**
     * 检测应用是否发布完成，并获取发布应用的信息
     */
    @GET("buildInfo")
    Call<BaseResponseBean<PgyCurrentAppDetailInfoBean>> getCurrentDetailInfo(@QueryMap Map<String, Object> map);
}
