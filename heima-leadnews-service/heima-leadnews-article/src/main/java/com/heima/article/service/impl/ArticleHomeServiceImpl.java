package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleHomeService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.heima.common.constants.ArticleConstants.*;

@Service
public class ArticleHomeServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ArticleHomeService {

    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 加载文章
     *
     * @param dto
     * @param loadtype 1 加载更多 2 加载最新
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short loadtype) {
        //校验参数
        Integer size = dto.getSize();
        if(size == null || size == 0){
            size = 10;
        }
        //分页最大值
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);

        //类型参数校验
        if (loadtype != LOADTYPE_LOAD_MORE && loadtype != LOADTYPE_LOAD_NEW) {
            loadtype = LOADTYPE_LOAD_MORE;
        }
        if(StringUtils.isBlank(dto.getTag())){
            dto.setTag(DEFAULT_TAG);
        }
        //时间校验
        if(dto.getMaxBehotTime() == null){
            dto.setMaxBehotTime(new Date());
        }
        if(dto.getMinBehotTime() == null) {
            dto.setMinBehotTime(new Date());
        }
        //查询数据
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto,loadtype);
        //结果封装
        return ResponseResult.okResult(apArticles);
    }

//    @Override
//    public ResponseResult load(ArticleHomeDto dto) {
//        List<ApArticle> apArticles = baseMapper.loadArticleList(dto);
//        return ResponseResult.okResult(apArticles);
//    }


}
