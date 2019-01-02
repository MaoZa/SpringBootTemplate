package cn.dawnland.springboottemplate.models.bo;

import com.ximuok.wb_ttyy.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Cap_Sub
 */
@Data
public class UserBO extends BaseModel {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("验证码")
    private String code;

}
