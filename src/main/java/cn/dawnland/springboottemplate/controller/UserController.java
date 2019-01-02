package cn.dawnland.springboottemplate.controller;

import com.ximuok.wb_ttyy.base.BaseController;
import com.ximuok.wb_ttyy.base.BaseModel;
import com.ximuok.wb_ttyy.models.LogLogin;
import com.ximuok.wb_ttyy.models.ProfileBO;
import com.ximuok.wb_ttyy.models.User;
import com.ximuok.wb_ttyy.models.UserSession;
import com.ximuok.wb_ttyy.models.bo.UserBO;
import com.ximuok.wb_ttyy.models.result.ReturnData;
import com.ximuok.wb_ttyy.service.LogLoginService;
import com.ximuok.wb_ttyy.service.SmsService;
import com.ximuok.wb_ttyy.service.UserService;
import com.ximuok.wb_ttyy.utils.AESOperator;
import com.ximuok.wb_ttyy.utils.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.jce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cap_Sub
 */
@RestController
@RequestMapping("user")
@Api(value = "用户接口", tags = {"用户接口"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private LogLoginService logLoginService;

    @ApiOperation("注册")
    @PostMapping("register")
    public ReturnData register(@RequestBody UserBO userBO){
//        Integer result = smsService.checkSmsIsEffective(mobile, code);
//        if(result < 1){
//            return ReturnData.isFail("验证码无效");
//        }
        if(userBO.getPassword().length() < 32){
            return ReturnData.isFail("密码有误");
        }
        if(!userBO.getMobile().startsWith("1") && !(userBO.getMobile().length() == 11)){
            return ReturnData.isFail("手机号错误");
        }
        Integer result = userService.selectByMobile(userBO.getMobile());
        if(result > 0){
            return ReturnData.isFail("手机号已存在");
        }
        userService.register(userBO.getMobile(), userBO.getPassword());
        return ReturnData.isOk().msg("注册成功");
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public ReturnData login(@RequestBody UserBO userBO,
                            HttpServletRequest request) throws Exception {
        User user = userService.loginCheck(userBO.getMobile(), userBO.getPassword());
        if(userBO.getPassword().length() < 32){
            return ReturnData.isFail("密码有误");
        }
        if (user == null) {
            return ReturnData.isFail("手机号或密码错误");
        }
        if(user.getDisable() == 1){
            return ReturnData.isFail("帐号已被封禁");
        }
        UserSession userSession = new UserSession(user.getId(), request.getRemoteAddr());
        request.getSession().setAttribute("userSession", userSession);
        LogLogin logLogin = new LogLogin(userSession.getId(), IPUtils.getIpAddress(request), new Date());
        logLoginService.insert(logLogin);
        user.setPassword(null);
        user.setCreateAt(null);
        user.setUpdateAt(null);
        return ReturnData.isOk().msg("登录成功").data(AESOperator.objectEncode(user));
    }

    @ApiOperation("重置密码")
    @PostMapping("resetPassword")
    public ReturnData resetPassword(@RequestBody UserBO userBO){
        Integer result = smsService.checkSmsIsEffective(userBO.getMobile(), userBO.getCode());
        if(userBO.getNewPassword().length() < 32){
            return ReturnData.isFail("密码有误");
        }
        if(result < 1){
            return ReturnData.isFail("验证码无效");
        }
        userService.resetPassword(userBO.getMobile(), userBO.getNewPassword());
        return ReturnData.isOk().msg("重置密码成功");
    }

    @ApiOperation("修改密码")
    @PostMapping("changePassword")
    public ReturnData changePassword(@RequestBody UserBO userBO,
                                     HttpServletRequest request){
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        User user = userService.get(userSession.getId());
        if(userBO.getNewPassword().length() < 32){
            return ReturnData.isFail("密码有误");
        }
        if(!user.getPassword().equals(userBO.getOldPassword())){
            return ReturnData.isFail("旧密码错误");
        }
        Integer result = userService.changePassword(user.getId(), userBO.getOldPassword(), userBO.getNewPassword());
        if(result == 1){
            return ReturnData.isOk().msg("修改密码成功");
        }
        return ReturnData.isFail("服务器繁忙");
    }

    @ApiOperation("更新用户资料")
    @PostMapping("updateProfile")
    public ReturnData updateProfile(@RequestBody ProfileBO profileBO,
                                    HttpSession session){
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        profileBO.setId(userSession.getId());
        userService.updateProfile(profileBO);
        return ReturnData.isOk().msg("更新成功");
    }

    @ApiOperation("退出登录")
    @PostMapping("logout")
    public ReturnData logout(HttpSession session){
        session.setAttribute("userSession", null);
        return ReturnData.isOk().msg("退出登录成功");
    }

    @ApiOperation("查询是否VIP")
    @PostMapping("findIsVIP")
    public ReturnData findIsVIP(@RequestBody BaseModel baseModel,
                                HttpSession session) throws Exception {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        User user = userService.get(userSession.getId());
        Map data = new HashMap(2);
        if(user.getExpireAt() != null){
            if(user.getExpireAt().compareTo(new Date()) >= 0){
                data.put("isVIP", 1);
                data.put("timeMillis", System.currentTimeMillis());
                userService.updateVIPLevel(user.getId(), 1, 0);
                return ReturnData.isOk().data(AESOperator.objectEncode(data));
            }
            data.put("isVIP", 0);
            userService.updateVIPLevel(user.getId(), 1, 0);
            data.put("timeMillis", System.currentTimeMillis());
            return ReturnData.isOk().data(AESOperator.objectEncode(data));
        }
        data.put("isVIP", 0);
        userService.updateVIPLevel(user.getId(), 1, 0);
        data.put("timeMillis", System.currentTimeMillis());
        return ReturnData.isOk().data(AESOperator.objectEncode(data));
    }
}
