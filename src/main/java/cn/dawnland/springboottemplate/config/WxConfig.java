package cn.dawnland.springboottemplate.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Configuration
@PropertySource("classpath:wx.properties")
@Component
public class WxConfig {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.mchId}")
    private String mchId;

    @Value("${wx.mchKey}")
    private String mchKey;

    @Value("${wx.tradeType}")
    private String tradeType;

    @Value("${wx.token}")
    private String token;

    @Value("${wx.aesKey}")
    private String aesKey;

    private static WxPayConfig config = null;

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getToken() {
        return token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public WxPayConfig getConfig() {
        if(config == null){
            config = new WxPayConfig();
            config.setAppId(appId);
            config.setMchId(mchId);
            config.setTradeType(tradeType);
            config.setMchKey(mchKey);
        }
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(appId);
        configStorage.setSecret(appSecret);
        configStorage.setToken(token);
        configStorage.setAesKey(aesKey);
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return wxMpService;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        return new WxMpMessageRouter(wxMpService);
    }
}
