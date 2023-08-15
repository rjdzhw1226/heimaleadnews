package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * 自媒体文章自动审核
     * @param id
     */
    @Override
    public void autoScanWmNews(Integer id) {
        //查询文章
        WmNews wmNews = wmNewsMapper.selectById(id);
        if(wmNews == null){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }
        //提取文本图片
        Map<String,Object> textAndImages = handleTextAndImages(wmNews);
        //审核文本内容 aliyun
        boolean isTextScan = handelTextScan((String) textAndImages.get("content"),wmNews);
        //审核图片 aliyun

        //审核成功 保存app文章
    }

    /**
     * 审核文本内容
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handelTextScan(String content, WmNews wmNews) {

        return false;
    }

    /**
     * 从自媒体文章中获取文本和图片
     * 提取封面图片
     * @param wmNews
     * @return
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {

        //文本内容
        StringBuilder stringBuilder = new StringBuilder();

        //图片内容
        List<String> images = new ArrayList<>();

        //从内容中获取文本和图片
        if(StringUtils.isNotBlank(wmNews.getContent())){
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if(map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }

                if(map.get("type").equals("image")){
                    images.add((String)map.get("value"));
                }
            }
        }

        //提取封面图片
        if(StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("content",stringBuilder.toString());
        resultMap.put("images",images);
        return resultMap;
    }
}
