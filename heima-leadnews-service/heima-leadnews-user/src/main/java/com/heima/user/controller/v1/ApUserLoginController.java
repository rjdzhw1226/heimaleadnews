package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.user.service.ApUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/login")
public class ApUserLoginController {

    //认证token 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
    //认证token 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
    //认证token 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

    @Autowired
    private ApUserService apUserService;

    @PostMapping("login_auth")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody LoginDto dto){
        return apUserService.login(dto);
    }

}
