package cn.dawnland.springboottemplate.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

/**
 * Model父类
 */
@Data
public class BaseModel extends Page {

    /**
     * 主键id
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(hidden = true)
    private Integer archive;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private  Date updateAt;

    /**
     * 时间戳
     */
    private Long timeMillis = System.currentTimeMillis();
}
