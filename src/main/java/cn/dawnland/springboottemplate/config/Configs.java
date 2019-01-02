package cn.dawnland.springboottemplate.config;

import cn.dawnland.springboottemplate.mapper.ConfigMapper;
import cn.dawnland.springboottemplate.models.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cap_Sub
 */
@Component
@Data
public class Configs {

    @Autowired
    private ConfigMapper configMapper;

    private List<Config> configList;

    private Map<String, String> configsMap = new HashMap<>();

    public Configs(List<Config> configList) {
        this.configList = configList;
        for (Config config : configList) {
            configsMap.put(config.getName(), config.getValue());
        }
    }
}
