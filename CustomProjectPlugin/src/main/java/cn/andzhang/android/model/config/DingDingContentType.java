package cn.andzhang.android.model.config;

/**
 * 钉钉群通知类型
 * @author zhangshuai
 */
public enum DingDingContentType {
    /**
     * 纯文案
     */
    TEXT("text"),
    /**
     * 跳转连接
     */
    LINK("link"),
    /**
     * 图片
     */
    PHOTO("photo"),
    /**
     * 文档
     */
    MARKDOWN("markdown"),
    /**
     * 卡片
     */
    ACTION_CARD("actionCard"),
    /**
     * 多链接卡片
     */
    FEED_CARD("feedCard");

    private final String mType;

    private DingDingContentType(String type) {
        this.mType = type;
    }

    public String getType() {
        return this.mType;
    }

    /**
     * 通过值获取枚举类型
     */
    public static String getValueByKey(String key) {
        for (DingDingContentType value : DingDingContentType.values()) {
            if (value.name().equals(key)) {
                return value.mType;
            }
        }
        return null;
    }
}
