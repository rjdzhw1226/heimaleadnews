package com.heima.model.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="login", description="登录前端")
public class LoginDto {
    //手机号
    @ApiModelProperty(value="手机号",required = true)
    private String phone;

    //密码
    @ApiModelProperty(value="密码",required = true)
    private String password;
}
