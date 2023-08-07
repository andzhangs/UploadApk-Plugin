package cn.andzhang.android.exts;

/**
 * @author zhangshuai
 * @date 2023/8/7 09:48
 * @mark 插件的扩展闭包配置
 */
public abstract class ConfigExtension {
    /**
     * 运行此插件之前的task
     */
    public String taskName;
    /**
     * 配置文件的地址
     */
    public String jsonPath;
}
