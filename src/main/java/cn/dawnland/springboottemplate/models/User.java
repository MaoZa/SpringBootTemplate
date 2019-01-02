package cn.dawnland.springboottemplate.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ximuok.wb_ttyy.base.BaseModel;
import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
public class User extends BaseModel {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 性别0男 1女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 签名
     */
    private String signature;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 坐标(GCJ-02,BD-09)
     */
    private String coord;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String district;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 封停状态 0正常 1封停
     */
    private Integer disable;

    /**
     * 微信号
     */
    private String wxNumber;

    /**
     * QQ号
     */
    private String tencentNumber;

    /**
     * 其他信息
     */
    private String other;

    /**
     * vip等级
     */
    private Integer vipLevel;

    /**
     * vip到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireAt;

    public User() {
    }

    public User(String mobile, String password) {
        this.nickname = mobile;
        this.mobile = mobile;
        this.password = password;
    }
}
