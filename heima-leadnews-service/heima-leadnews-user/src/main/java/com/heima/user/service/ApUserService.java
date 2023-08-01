package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;

public interface ApUserService extends IService<ApUser> {
    /**
     * app端登录
     */
    public ResponseResult login(LoginDto dto);
}
