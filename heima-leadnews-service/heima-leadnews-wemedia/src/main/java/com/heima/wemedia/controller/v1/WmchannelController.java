package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ap1/v1/channel")
public class WmchannelController {

    @GetMapping("/channels")
    public ResponseResult findAll(){
        return null;
    }
}
