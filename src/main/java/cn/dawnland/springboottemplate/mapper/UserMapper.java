package cn.dawnland.springboottemplate.mapper;

import com.ximuok.wb_ttyy.models.ProfileBO;
import com.ximuok.wb_ttyy.models.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Cap_Sub
 */
@Mapper
public interface UserMapper {

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Insert("insert into user(mobile, password, nickname, create_at)" +
            "values(#{mobile}, #{password}, #{nickname}, now())")
    Integer insert(User user);

    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @Select("select * from user where mobile = #{mobile} and password = #{password} and archive = 0")
    User selectByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);

    /**
     * vip时间增加(月)
     * @param id
     * @param month
     * @return
     */
    @Update("update user set expire_at = DATE_ADD(IFNULL(expire_at, now()), INTERVAL #{month} MONTH) where id = #{id}")
    Integer addVipexpireAt(@Param("id") Long id, @Param("month") Integer month);

    /**
     * 更新VIP等级
     */
    @Update("update user set vip_level = #{newVipLevel} where id = #{id} and vip_level = #{oldVipLevel}")
    Integer updateVIPLevelBYUserId(@Param("id") Long id, @Param("newVipLevel") Integer newVipLevel, @Param("oldVipLevel") Integer oldVipLevel);

    /**
     * 帐号封禁
     * @param id
     * @return
     */
    @Update("update user set disable = 1 where id = #{id}")
    Integer disable(@Param("id") Long id);

    /**
     * id查用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User get(@Param("id") Long id);

    @Update("update user set password = #{newPassword} where id = #{id} and password = #{oldPassword}")
    Integer changePassword(@Param("id") Long id, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    @Update("update user set password = #{newPassword} where mobile = #{mobile}")
    Integer resetPassword(@Param("mobile") String mobile, @Param("newPassword") String newPassword);

    @UpdateProvider(type = UserProvider.class, method = "updateProfile")
    Integer updateProfile(ProfileBO profileBO);

    @Select("select count(1) from user where mobile = #{mobile} and archive = 0")
    Integer selectByMobile(@Param("mobile") String mobile);

    // TODO: 2018/12/13 非空更新

    // TODO: 2018/12/13 条件查询

    class UserProvider{
        public String updateProfile(ProfileBO profileBO){
            return new SQL(){{
                UPDATE("user");
                if (profileBO.getNickname() != null) {
                    SET("nickname = #{nickname}");
                }
                if (profileBO.getAge() != null) {
                    SET("age = #{age}");
                }
                if (profileBO.getAvatar() != null) {
                    SET("avatar = #{avatar}");
                }
                if (profileBO.getEmail() != null) {
                    SET("email = #{email}");
                }
                if (profileBO.getSex() != null) {
                    SET("sex = #{sex}");
                }
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}
