package com.jzy.api.constant;

public class WechatConstant {
    /*---------------------------------------Interface link---------------------------------------*/

    /**
     * url目的：用户同意授权，获取code。如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     * code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     *
     *      URL后缀参数   是否必填   说明
     * @param appid	        是	    公众号的唯一标识
     * @param scope	        是	    应用授权作用域，
     * snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
     * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     */
    public static String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    /**
     * 网页询问授权
     */
    public static String website_oauth_url = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    /**
     * 通过code换取网页授权access_token
     *        URL后缀参数   是否必填   说明
     * @param code	        是	       填写第一步获取的code参数
     * @param grant_type	是	填写为authorization_code
     */
    public static String oauth_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 使用oauth_url获取到的access_token拉取用户信息(需scope为snsapi_userinfo)
     */
    public static String snsuserinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    /**
     * token
     */
    public static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /*-------------------------------------Interface parameter------------------------------------*/

    /**
     * 错误码
     */
    public static final String ERRCODE = "errcode";
    /**
     * 错误信息
     */
    public static final String ERRMSG = "errmsg";
    /**
     * 接口调用凭证
     */
    public static final String ACCESS_TOKEN = "access_token";
    /**
     * 凭证超时时间，单位（秒）
     */
    public static final String EXPIRES_IN = "expires_in";
    /**
     * 刷新access_token
     */
    public static final String REFRESH_TOKEN = "refresh_token";
    /**
     * 标识
     */
    public static final String OPENID = "openid";
    /**
     * 作用域
     */
    public static final String SCOPE = "scope";
    /**
     * 用户授权作用域
     */
    public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";
    public static final String SCOPE_SNSAPI_BASE = "snsapi_base";
    /**
     * 网页应用目前仅填写snsapi_login
     */
    public static final String SCOPE_SNSAPI_LOGIN = "snsapi_login";

    /*------------------------------------snsuserinfo_url返回的数据信息------------------------------------*/

    /**
     * 昵称
     */
    public static final String SNSAPI_USERINFO_NICKNAME = "nickname";
    /**
     * 性别
     */
    public static final String SNSAPI_USERINFO_SEX = "sex";
    /**
     * 国家
     */
    public static final String SNSAPI_USERINFO_COUNTRY = "country";
    /**
     * 省份
     */
    public static final String SNSAPI_USERINFO_PROVINCE = "province";
    /**
     * 城市
     */
    public static final String SNSAPI_USERINFO_CITY = "city";
    /**
     * 头像
     */
    public static final String SNSAPI_USERINFO_HEADIMGURL = "headimgurl";
    /**
     * 特权信息
     */
    public static final String SNSAPI_USERINFO_PRIVILEGE = "privilege";
    /**
     * 语言
     */
    public static final String SNSAPI_USERINFO_LANGUAGE = "language";
    /**
     * 绑定Id
     */
    public static final String SNSAPI_USERINFO_UNIONID = "unionid";


    /*-------------------------------------MsgType:消息类型------------------------------------*/

    /**
     * 消息类型
     */
    public static final String MSGTYPE = "MsgType";
    /**
     * 消息类型 - text:文本
     */
    public static final String MSGTYPE_TEXT = "text";
    /**
     * 消息类型 - voice:语音
     */
    public static final String MSGTYPE_VOICE = "voice";
    /**
     * 消息类型 - video:视频
     */
    public static final String MSGTYPE_VIDEO = "video";
    /**
     * 消息类型 - shortvideo:小视频
     */
    public static final String MSGTYPE_SHORTVIDEO = "shortvideo";
    /**
     * 消息类型 - location:地理位置
     */
    public static final String MSGTYPE_LOCATION = "location";
    /**
     * 消息类型 - link:链接
     */
    public static final String MSGTYPE_LINK = "link";
    /**
     * 消息类型 - event:事件
     */
    public static final String MSGTYPE_EVENT = "event";

    /*--------------------------------------Event:事件类型-------------------------------------*/

    /**
     * 订阅
     */
    public static final String EVENT_SUBSCRIBE = "subscribe";
    /**
     * 取消订阅
     */
    public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
    /**
     * 地理位置
     */
    public static final String EVENT_LOCATION = "LOCATION";
    /**
     * 自定义事件
     */
    public static final String EVENT_CLICK = "click";
}
