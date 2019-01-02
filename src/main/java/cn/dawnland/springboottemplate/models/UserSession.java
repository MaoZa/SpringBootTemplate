package cn.dawnland.springboottemplate.models;

import lombok.Data;

@Data
public class UserSession {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录ip
     */
    private String loginIp;

    public UserSession() {
    }

    public UserSession(Long id, String loginIp) {
        this.id = id;
        this.loginIp = loginIp;
    }
}
