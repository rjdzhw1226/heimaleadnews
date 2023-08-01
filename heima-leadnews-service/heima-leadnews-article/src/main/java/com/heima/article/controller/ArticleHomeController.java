package com.heima.article.controller;

import com.heima.article.service.ArticleHomeService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private ArticleHomeService articleHomeService;


    @PostMapping({"/load","/loadmore","/loadnew"})
    public ResponseResult load(@RequestBody ArticleHomeDto dto){
        return articleHomeService.load(dto);
    }

}
