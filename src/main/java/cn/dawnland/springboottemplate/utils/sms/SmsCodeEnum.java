package cn.dawnland.springboottemplate.utils.sms;

/**
 * Created by liuyong
 * 2018-11-22  16-44
 */

public enum SmsCodeEnum {


    ID_CHECK_CODE("身份校验验证码","SMS_145490006"),
    LOGIN_CHECK_CODE("登录确认验证码","SMS_145490005"),
    ERROE_LOGIN_CODE("登录异常验证码","SMS_145490004"),
    REGISTER_CHECK_CODE("用户注册验证码","SMS_145490003"),
    PASSWORD_CHANGE_CODE("修改密码验证码","SMS_145490002"),
    INFO_CHANGE_CODE("信息变更验证码","SMS_145490001"),

    ;

    private String message;

    private String code;

    SmsCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static SmsCodeEnum getEnum(int type){
        SmsCodeEnum smsCodeEnum;
        switch (type){
            case 1:
                smsCodeEnum = ID_CHECK_CODE;
                break;
            case 2:
                smsCodeEnum = LOGIN_CHECK_CODE;
                break;
            case 3:
                smsCodeEnum = ERROE_LOGIN_CODE;
                break;
            case 4:
                smsCodeEnum = REGISTER_CHECK_CODE;
                break;
            case 5:
                smsCodeEnum = PASSWORD_CHANGE_CODE;
                break;
            case 6:
                smsCodeEnum = INFO_CHANGE_CODE;
                break;
            default:
                smsCodeEnum = ID_CHECK_CODE;
                break;
        }
        return smsCodeEnum;
    }
}
