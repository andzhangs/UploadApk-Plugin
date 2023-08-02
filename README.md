## 前言：
#### &emsp;&emsp;使用此插件在运行打包命令时，自动上传到蒲公英或者FirIm发布平台，并通知到钉钉群。


## 引入依赖

### 第一步：配置项目级 "build.gradle" 文件:
LastVersion：[![](https://jitpack.io/v/TooCareAboutYOU/UploadApk-Plugin.svg)](https://jitpack.io/#TooCareAboutYOU/UploadApk-Plugin)
```
    buildscript {
         dependencies {
            classpath 'com.github.TooCareAboutYOU:UploadApk-Plugin:LastVersion'
         }
    }
```

### 第二步：配置项目级 "setting.gradle" 文件:
```
    pluginManagement {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
```

### 第三步：配置模块级 "build.gradle" 文件:
```
    plugins {
        id 'upload-apk-plugin'
    }
```

### 第四步：项目最外层新建 "upload-apk.json" 文件，写入配置，实例见下文。

添加完成后重新rebuild，项目编译成功即可使用。


### 在配置完app签名和插件参数文件后，运行命令: assembleRelease 即可。



## 插件参数配置
1、选填配置：pgyConfig、firImConfig</br>
2、必填配置：ddConfig、ddContent</br>
3、ddContent的内容根据自定义的 msgtype 选择对应的json参数



```
{
  "apkOutputPath": "xxx/xxx/xxx/app-release.apk",
  "isPgy": false,
  "pgyConfig": {
    "pgyAppKey": "",
    "pgyApiKey": "",
    "pgyBuildType": "android",
    "apkName": "app-release.apk",
    "pgyOversea": 2,
    "pgyBuildInstallType": 1,
    "pgyBuildPassword": "",
    "pgyBuildDescription": "",
    "pgyBuildUpdateDescription": "",
    "pgyBuildInstallDate": 2,
    "pgyBuildInstallStartDate": "",
    "pgyBuildInstallEndDate": "",
    "pgyBuildChannelShortcut": ""
  },
  "firImConfig": {
    "type": "android",
    "packageName": "xxx.xxx.xxx",
    "bindingHost": "",
    "apiToken": "",
    "icon": "xxxx/xxx/xxx/icon.png",
    "xName": "AppName",
    "xVersion": "1.0.0",
    "xBuild": "1",
    "xChangeLog": "Fir.im版本更新"
  },
  "ddConfig": {
    "ddWebSecret": "",
    "ddWebHookUrl": ""
  },
  "ddContent": {
    "at": {
      "atMobiles": [
        "188****1234"
      ],
      "atUserIds": [
        "id188****1234"
      ],
      "isAtAll": false
    },
    "msgtype": "link",
    "text": {
      "content": "我是来自蒲公英Apk动态发布的消息测试用例 @id188****1234 "
    },
    "link": {
      "text": "我是来自蒲公英的Apk动态发布的消息测试用例",
      "title": "Release version",
      "picUrl": "", 
      "messageUrl": ""  
    },
    "photo": {
      "photoURL": ""
    },
    "markdown": {
      "title": "上海天气",
      "text": "#### 上海天气 @zs18874703156 \n > 9度，西北风1级，空气良89，相对温度73%\n > ![screenshot](https://www.pgyer.com/app/qrcodeHistory/2043a9d570d91ffb95da85e6ee745108ead34c27c7b7f7947bfb35ca44235055)\n > ###### 17点50分发布 [天气](https://www.dingtalk.com) \n"
    },
    "actionCard": {
      "title": "我是Apk动态发布的消息测试用例",
      "text": "![screenshot](https://www.pgyer.com/app/qrcodeHistory/2043a9d570d91ffb95da85e6ee745108ead34c27c7b7f7947bfb35ca44235055) 我是Apk动态发布的消息测试用例",
      "btnOrientation": "0",
      "singleTitle": "阅读全文",
      "singleURL": "" 
    },
    "feedCard": {
      "links": [
        {
          "title": "我是Apk动态发布的消息测试用例-1",
          "messageURL": "", 
          "picURL": "https://www.pgyer.com/app/qrcodeHistory/2043a9d570d91ffb95da85e6ee745108ead34c27c7b7f7947bfb35ca44235055"
        },
        {
          "title": "我是Apk动态发布的消息测试用例-2",
          "messageURL": "", 
          "picURL": "https://www.pgyer.com/app/qrcodeHistory/2043a9d570d91ffb95da85e6ee745108ead34c27c7b7f7947bfb35ca44235055"
        },
        {
          "title": "我是Apk动态发布的消息测试用例-3",
          "messageURL": "",  
          "picURL": "https://www.pgyer.com/app/qrcodeHistory/2043a9d570d91ffb95da85e6ee745108ead34c27c7b7f7947bfb35ca44235055"
        }
      ]
    }
  }
}
```

## 字段说明：

### 1、基础字段：
```
//(必填)本地Apk文件的本地绝对路径
public String apkOutputPath;
//(必填)true：蒲公英，false：fir.im
public boolean isPgy;
//(选填)蒲公英配置
public PgyConfigBean pgyConfig;
//(选填)Fir.im配置
public FirImConfigBean firImConfig;
//(选填)钉钉配置
public DdConfigBean ddConfig;
//(选填)发送钉钉消息内容
public DingDingNewsBean ddContent;
```

### 2、蒲公英 配置字段：
```
//(必填)蒲公英账号生成的APP KEY
public String pgyAppKey;
//(必填) API Key
public String pgyApiKey;
//(必填) 需要上传的应用类型，如果是iOS类型请传ios或ipa，如果是Android类型请传android或apk
public String pgyBuildType;
//(必填)apk名称
public String apkName = "app-release.apk";
//(选填) 是否使用海外加速上传，值为：1 使用海外加速上传，2 国内加速上传；留空根据 IP 自动判断海外加速或国内加速
public int pgyOversea;
//(选填)应用安装方式，值为(1,2,3，默认为1 公开安装)。1：公开安装，2：密码安装，3：邀请安装
public int pgyBuildInstallType;
//(选填) 设置App安装密码，密码为空时默认公开安装
public String pgyBuildPassword;
//(选填) 应用介绍，如没有介绍请传空字符串，或不传。
public String pgyBuildDescription;
//(选填) 版本更新描述，请传空字符串，或不传。
public String pgyBuildUpdateDescription;
//(选填)是否设置安装有效期，值为：1 设置有效时间， 2 长期有效，如果不填写不修改上一次的设置
public int pgyBuildInstallDate;
//(选填)安装有效期开始时间，字符串型，如：2018-01-01
public String pgyBuildInstallStartDate;
//(选填)安装有效期结束时间，字符串型，如：2018-12-31
public String pgyBuildInstallEndDate;
//(选填)所需更新指定的渠道短链接，渠道短链接须为已创建成功的，并且只可指定一个渠道，字符串型，如：abcd
public String pgyBuildChannelShortcut;
```

### 3、Fir-Im 配置字段：
```
//(必填)ios 或者 android
public String type;
//(必填)App 的 bundleId
public String packageName;
//(必填)长度为 32, 用户在 fir 的 api_token
public String apiToken;
//(选填)fir仓库域名
public String bindingHost;
//(选填)appLogo 为空的话就不上传icon
public String icon;
//(选填)应用名称（上传 ICON 时不需要）
public String xName;
//(选填)版本号（上传 ICON 时不需要）
public String xVersion;
//(选填)Build 号（上传 ICON 时不需要）
public String xBuild;
//(选填)更新日志（上传 ICON 时不需要）
public String xChangeLog;
```

### 4、钉钉群自定义机器人 配置字段：
```
//(必填)WebSecret
public String ddWebSecret;
//(必填)WebHookUrl
public String ddWebHookUrl;
```

#### 5、消息基础配置:
```
//(选填)通知指定用户
public AtBean at;
//(必填)群通知消息类型
public String msgtype;
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
```

* #### 5.1、指定群通知艾特用户:
```
    //(选填)被@人的手机号。说明 消息内容text内要带上"@手机号"，跟atMobiles参数结合使用，才有@效果。在content里添加@人的手机号，且只有在群内的成员才可被@，非群内成员手机号会被脱敏。
    public String[] atMobiles;
    //(选填)被@人的用户userid。在content里添加@人的userid。
    public String[] atUserIds;
    //(选填)是否@所有人是true，否则为false
    public boolean isAtAll = false;
```

* #### 5.2、纯文本消息:
```
    //(必填)消息文案
    public String content;
```

* #### 5.3、跳转链接消息:
```
    //(必填)文案
    public String text;
    //(必填)标题
    public String title;
    //(选填)图片链接
    public String picUrl;
    //(选填)跳转链接
    public String messageUrl;
```

* #### 5.4、纯图片消息:
```
    //(必填)消息文案
    public String content;
```

* #### 5.5、MarkDown格式消息:
```
    //(必填)首屏会话透出的展示内容。
    public String title;
    //(必填)Markdown格式的消息内容。
    public String text;
```

* #### 5.6、整体卡片跳转消息:
```
    //(必填)首屏会话透出的展示内容。
    public String title;
    //(必填)markdown格式的消息内容。
    public String text;
    //(必填)0：按钮竖直排列 / 1：按钮横向排列
    public int btnOrientation = 0;
    //(选填)样式一 单个按钮的标题。
    public String singleTitle;
    //(选填)跳转链接,单个按钮的跳转链接。
    public String singleURL;
    //(选填)样式二 按钮。
    public BtnsBean[] btns;
    
    //点击消息
        //(必填)按钮标题。
        public String title;
        //(选填)点击按钮触发的URL。
        public String actionURL;
```

* ##### 5.7、单独卡片跳转消息:
```
    //(必填)单条信息文本。
    public String title;
    //(选填)单条信息跳转链接
    public String messageURL;
    //(选填)单条信息后面图片的URL
    public String picURL;
```

### 参考资料：
&emsp;&emsp;钉钉API: <https://open.dingtalk.com/document/group/custom-robot-access></br>
&emsp;&emsp;蒲公英API：<https://www.pgyer.com/doc/view/api#fastUploadApp></br>
&emsp;&emsp;Fir.im：<https://www.betaqr.com/apps></br>


#### <font color="#dddd00">注：发送到钉钉的消息内容，需要包含钉钉机器人配置的关键字</font>
