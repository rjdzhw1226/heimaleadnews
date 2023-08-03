package com.heima.user;

import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SQLTest {

    @Autowired
    private ApUserMapper apUserMapper;

    @Test
    public void SQLTestConnect(){
        ApUser apUser = apUserMapper.selectById(1);
        log.info("用户为：{}",apUser);
    }
}
