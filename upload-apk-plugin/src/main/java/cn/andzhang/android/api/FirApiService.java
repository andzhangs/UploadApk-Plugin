package cn.andzhang.android.api;

import java.util.List;
import java.util.Map;

import cn.andzhang.android.model.response.fir.FirApkDetailBean;
import cn.andzhang.android.model.response.fir.FirApplicationBean;
import cn.andzhang.android.model.response.fir.FirImTokenBean;
import cn.andzhang.android.model.response.fir.FirImUploadBean;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/28 18:44
 * @description Fir.im
 */
public interface FirApiService {

    /**
     * 获取Fir.im的上传凭证
     */
    @FormUrlEncoded
    @POST(".")
    Call<FirImTokenBean> getFirImToken(@FieldMap Map<String, String> map);

    /**
     * 获取应用列表
     */
    @GET(".")
    Call<FirApplicationBean> getApplicationList(@Query("api_token") String apiToken);

    /**
     * 上传APk文件
     */
    @Multipart
    @POST
    Call<FirImUploadBean> uploadToFirIm(@Url String url, @Part List<MultipartBody.Part> parts);

    /**
     * 获取应用详细信息
     */
    @GET("{id}")
    Call<FirApkDetailBean> getApkDetailInfo(@Path("id") String id, @Query("api_token") String apiToken);
}
