package cn.dawnland.springboottemplate.service.impl;

import com.ximuok.wb_ttyy.mapper.UserMapper;
import com.ximuok.wb_ttyy.models.ProfileBO;
import com.ximuok.wb_ttyy.models.User;
import com.ximuok.wb_ttyy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cap_Sub
 */
@Service
@Transactional
public class UserServerImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer register(String mobile, String password) {
        User user = new User(mobile, password);
        return userMapper.insert(user);
    }

    @Override
    public User loginCheck(String mobile, String password) {
        return userMapper.selectByMobileAndPassword(mobile, password);
    }

    @Override
    public Integer addVipexpireAt(Long id, Integer day) {
        return userMapper.addVipexpireAt(id, day);
    }

    @Override
    public Integer disable(Long id) {
        return userMapper.disable(id);
    }

    @Override
    public User get(Long id) {
        return userMapper.get(id);
    }

    @Override
    public Integer changePassword(Long id, String oldPassword, String newPassword) {
        return userMapper.changePassword(id, oldPassword, newPassword);
    }

    @Override
    public Integer resetPassword(String mobile, String newPassword) {
        return userMapper.resetPassword(mobile, newPassword);
    }

    @Override
    public Integer updateProfile(ProfileBO profileBO) {
        return userMapper.updateProfile(profileBO);
    }

    @Override
    public Integer selectByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public Integer updateVIPLevel(Long id, Integer newVipLevel, Integer oldVipLevel) {
        return userMapper.updateVIPLevelBYUserId(id, newVipLevel, oldVipLevel);
    }
}
