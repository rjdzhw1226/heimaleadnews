package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * app端登录
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        //(手机号加密码登录)
        if(!StringUtils.isBlank(dto.getPhone()) && !StringUtils.isBlank(dto.getPassword())){
            //查询用户
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if(apUser == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
            }
            //加盐对比密码
            String salt = apUser.getSalt();
            String password = dto.getPassword();
            password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if(!password.equals(apUser.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR, "密码错误");
            }
            //jwt验证
            //游客 0 正常用户 密匙
            Map<String, Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        }else {
            //游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0l));
            return ResponseResult.okResult(map);
        }
    }


}
