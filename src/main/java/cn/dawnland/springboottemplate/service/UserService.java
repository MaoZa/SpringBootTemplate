package cn.dawnland.springboottemplate.service;

import com.ximuok.wb_ttyy.models.ProfileBO;
import com.ximuok.wb_ttyy.models.User;

/**
 * @author Cap_Sub
 */
public interface UserService {

    /**
     * 注册用户
     * @param mobile
     * @param password
     * @return
     */
    Integer register(String mobile, String password);

    /**
     * 用户登录校验
     * @param mobile
     * @param password
     * @return
     */
    User loginCheck(String mobile, String password);

    /**
     * 添加vip到期时间
     * @param id
     * @param day
     * @return
     */
    Integer addVipexpireAt(Long id, Integer day);

    /**
     * 封禁用户
     * @param id
     * @return
     */
    Integer disable(Long id);

    /**
     * id获取用户
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * 修改用户密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Integer changePassword(Long id, String oldPassword, String newPassword);

    /**
     * 重置密码
     * @param mobile
     * @param newPassword
     * @return
     */
    Integer resetPassword(String mobile, String newPassword);

    /**
     * 更新用户资料
     * @param profileBO
     * @return
     */
    Integer updateProfile(ProfileBO profileBO);

    /**
     * 手机号查用户
     * @param mobile
     * @return
     */
    Integer selectByMobile(String mobile);


    /**
     * 更新VIP等级
     * @param id
     * @param newVipLevel
     * @param oldVipLevel
     * @return
     */
    Integer updateVIPLevel(Long id, Integer newVipLevel, Integer oldVipLevel);

}
