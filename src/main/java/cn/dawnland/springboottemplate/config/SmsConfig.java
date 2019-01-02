package cn.dawnland.springboottemplate.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Cap_Sub
 */
@Component
@Data
public class SmsConfig {

    /**
     * 短信过期时间120s
     */
    private Integer EXPIRE_TIME;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    private String domain;

    private String accessKeyId;

    private String accessKeySecret;

    private String SIGN_NAME;

    public void setConfig(Integer EXPIRE_TIME, String domain, String accessKeyId, String accessKeySecret, String SIGN_NAME){
        this.EXPIRE_TIME = EXPIRE_TIME;
        this.domain = domain;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.SIGN_NAME = SIGN_NAME;
    }
}
