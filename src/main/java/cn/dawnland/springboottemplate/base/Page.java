package cn.dawnland.springboottemplate.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Cap_Sub
 */
@Data
public class Page {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 条数
     */
    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 20;
}
