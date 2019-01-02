package cn.dawnland.springboottemplate.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Cap_Sub
 */
@Data
@Component
public class PayConfig {

    private String notifyUrl;

    private String returnUrl;

    private String appid;

    private String sign;

    private String version;

    private String lang;

    private String modal;

    public void setConfig(String notifyUrl, String returnUrl, String appid, String sign, String version, String lang, String modal){
        this.notifyUrl = notifyUrl;
        this.returnUrl = returnUrl;
        this.appid = appid;
        this.sign = sign;
        this.version = version;
        this.lang = lang;
        this.modal = modal;
    }
}
