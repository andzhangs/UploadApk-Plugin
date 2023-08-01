package cn.andzhang.android.model.config;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author zhangshuai@attrsense.com
 * @date 2022/11/29 18:51
 * @description
 */
public class DingDingNewsBean implements Serializable {
    //通知指定用户
    public AtBean at;
    //(必填)群通知消息类型
    public String msgtype = "";
    //(选填)类型一：纯文本
    public TextBean text;
    //(选填)类型二：跳转链接
    public LinkBean link;
    //(选填)类型三：纯图片
    public PhotoBean photo;
    //(选填)类型四：markdown格式
    public MarkdownBean markdown;
    //(选填)类型五：整体跳转卡片
    public ActionCardBean actionCard;
    //(选填)类型六：独立跳转卡片
    public FeedCardBean feedCard;

    /**
     * 用户相关
     */
    public static class AtBean implements Serializable {
        //被@人的手机号。说明 消息内容text内要带上"@手机号"，跟atMobiles参数结合使用，才有@效果.
        //在content里添加@人的手机号，且只有在群内的成员才可被@，非群内成员手机号会被脱敏。
        public String[] atMobiles;
        //被@人的用户userid
        //在content里添加@人的userid。
        public String[] atUserIds;
        //@所有人是true，否则为false
        public boolean isAtAll = false;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * 文本
     * 消息类型，此时固定为：text。
     */
    public static class TextBean implements Serializable {
        //(必填)消息文案
        public String content = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * 链接
     * 消息类型，此时固定为：link。
     */
    public static class LinkBean implements Serializable {
        //(必填)文案
        public String text = "";
        //(必填)标题
        public String title = "";
        //(选填)图片链接
        public String picUrl = "";
        //(选填)跳转链接
        public String messageUrl = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }


    /**
     * 图片
     */
    public static class PhotoBean implements Serializable {
        //(必填)图片链接
        public String photoURL = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * Markdown
     * 消息类型，此时固定为：markdown。
     */
    public static class MarkdownBean implements Serializable {
        //(必填)首屏会话透出的展示内容。
        public String title = "";
        //(必填)Markdown格式的消息内容。
        public String text = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * 整体跳转
     * 消息类型，此时固定为：actionCard。
     */
    public static class ActionCardBean implements Serializable {
        //(必填)首屏会话透出的展示内容。
        public String title = "";
        //(必填)markdown格式的消息内容。
        public String text = "";
        //(必填)0：按钮竖直排列 / 1：按钮横向排列
        public int btnOrientation = 0;
        //(选填)样式一 单个按钮的标题。
        public String singleTitle = "";
        //(选填)跳转链接,单个按钮的跳转链接。
        public String singleURL = "";
        //(选填)样式二 按钮。
        public BtnsBean[] btns;


        public static class BtnsBean implements Serializable {
            //(必填)按钮标题。
            public String title = "";
            //(选填)点击按钮触发的URL。
            public String actionURL = "";

            @Override
            public String toString() {
                return new Gson().toJson(this);
            }
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * FeedCard类型
     * 此消息类型为固定feedCard。
     */
    public static class FeedCardBean implements Serializable {
        public FeedCardLinksBean[] links;

        public static class FeedCardLinksBean implements Serializable {
            //(必填)单条信息文本。
            public String title = "";
            //(选填)单条信息跳转链接
            public String messageURL = "";
            //(选填)单条信息后面图片的URL
            public String picURL = "";


            @Override
            public String toString() {
                return new Gson().toJson(this);
            }
        }


        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * 填充消息模板
     * ---------------------------------------------------------------------------------------------
     * <p>
     * description：
     */
    public MsgParamMapBean msg_param_map;
    public MsgMediaIdParamMapBean msg_media_id_param_map;

    public static class MsgParamMapBean implements Serializable {
        public String type_name = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public static class MsgMediaIdParamMapBean implements Serializable {
        public String img_media_id = "";

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}