package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleHomeService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleHomeServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ArticleHomeService {

    /**
     * 加载文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto) {
        List<ApArticle> apArticles = baseMapper.loadArticleList(dto);
        return ResponseResult.okResult(apArticles);
    }
}
