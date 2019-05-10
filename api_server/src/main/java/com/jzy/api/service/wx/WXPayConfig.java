package com.jzy.api.service.wx;

import com.jzy.api.constant.PayConfig;
import com.jzy.api.service.biz.IWXPayDomain;
import com.jzy.api.service.biz.impl.WXPayDomainSimpleImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Data
@Slf4j
public class WXPayConfig {

    private static final WXPayConfig instance = new WXPayConfig();
    private byte[] certData;

    private WXPayConfig() {
        log.debug("WXPayConfig init");
        try {

            String certPath = "/opt/package_manage/package_backup/1395455402_apiclient_cert.p12";

            log.debug("整数地址为：" + certPath);

            File file = new File(certPath);

            InputStream certStream = new FileInputStream(file);

            this.certData = new byte[(int) file.length()];

            certStream.read(this.certData);

            certStream.close();
        } catch (FileNotFoundException e2) {
//            log.error("wechat证书获取不到.", e2);
        } catch (IOException e) {
            log.error("wechat加载证书异常.", e);
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
        return PayConfig.getWxAppId();
    }


    /**
     * 获取 Mch ID
     *
     * @return Mch ID 1516797471
     */
    public String getMchID() {
        return PayConfig.getWxMchId();
    }


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public String getKey() {
        return PayConfig.getWxKey();
    }

    /**
     * 获取 notify_url
     *
     * @return notify_url
     */
    public String getNotifyUrl() {
        return PayConfig.getDomainUrl() + PayConfig.getWxNotifyUrl();
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
