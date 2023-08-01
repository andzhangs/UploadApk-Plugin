package cn.andzhang.android.model.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/28 18:42
 * @description
 */
public class BaseResponseBean<T> implements Serializable {
    public Integer code;
    public String message;
    public T data;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}