package cn.dawnland.springboottemplate.listener;

import cn.dawnland.springboottemplate.config.Configs;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Cap_Sub
 */
public class AppStartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        contextRefreshedEvent.getApplicationContext().getBean(Configs.class);
    }
}
