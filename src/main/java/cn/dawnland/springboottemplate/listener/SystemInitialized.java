package cn.dawnland.springboottemplate.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统初始化
 * @author Cap_Sub
 */
@Component
public class SystemInitialized implements ApplicationRunner {

//    @Autowired
//    private ConfigMapper configMapper;
//    @Autowired
//    private PayConfig payConfig;
//    @Autowired
//    private SmsConfig smsConfig;

    private Logger logger = LoggerFactory.getLogger(SystemInitialized.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        InitializedConfig();

        logger.info("-------->系统初始化完成....");
    }

    /**
     * 数据库配置初始化
     */
    public void InitializedConfig(){
//        Map<String, String> configMap = ListToMapUtil.ConfigListToMap(configMapper.findConfigList());
//        payConfig.setConfig(configMap.get("notifyUrl"), configMap.get("returnUrl"),
//                configMap.get("appid"), configMap.get("sign"), configMap.get("version"),
//                configMap.get("lang"), configMap.get("modal"));
//        smsConfig.setConfig(Integer.parseInt(configMap.get("effectiveSecond")), configMap.get("domain"),
//                configMap.get("accessKeyId"), configMap.get("accessKeySecret"), configMap.get("SIGN_NAME"));
    }
}
