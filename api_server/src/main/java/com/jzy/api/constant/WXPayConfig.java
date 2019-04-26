package com.jzy.api.constant;

import com.jzy.api.base.CustomerContextAware;
import com.jzy.api.service.sys.ConfigService;
import com.jzy.api.util.IWXPayDomain;
import com.jzy.api.util.WXPayDomainSimpleImpl;
import org.apache.log4j.Logger;

import java.io.*;

public class WXPayConfig {

    private static Logger logger = Logger.getLogger(WXPayConfig.class);
    private static ConfigService config = (ConfigService) CustomerContextAware.getBean(ConfigService.class);
    private static final WXPayConfig instance = new WXPayConfig();
    private byte[] certData;

    private WXPayConfig() {
        try {
            String certPath = this.getClass().getResource("").getPath().concat(config.value("wx_mch_id").concat("_apiclient_cert.p12"));

            File file = new File(certPath);

            InputStream certStream = new FileInputStream(file);

            this.certData = new byte[(int) file.length()];

            certStream.read(this.certData);

            certStream.close();
        } catch (IOException e) {
            logger.error("wechat加载证书异常.", e);
        }
    }

    public static WXPayConfig getInstance() {
        return instance;
    }

    /**
     * 获取 App ID
     *
     * @return App ID wx7e2e50acd39b08bf
     */
    public String getAppID() {
        return config.value("wx_appid");
    }


    /**
     * 获取 Mch ID
     *
     * @return Mch ID 1516797471
     */
    public String getMchID() {
        return config.value("wx_mch_id");
    }


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public String getKey() {
        return config.value("wx_key");
    }

    /**
     * 获取 notify_url
     *
     * @return notify_url
     */
    public String getNotifyUrl() {
        return config.valueDomainUrl("wx_notify_url");
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6 * 1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8 * 1000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

}
