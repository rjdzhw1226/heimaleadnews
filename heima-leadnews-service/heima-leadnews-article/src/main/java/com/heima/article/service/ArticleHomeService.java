package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

public interface ArticleHomeService extends IService<ApArticle> {
    /**
     * 加载文章
     * @param dto
     * @return
     */
    ResponseResult load(ArticleHomeDto dto, Short loadtype);
}
