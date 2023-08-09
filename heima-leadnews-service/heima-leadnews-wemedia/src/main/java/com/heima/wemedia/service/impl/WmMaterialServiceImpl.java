package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private  WmMaterialMapper wmMaterialMapper;

    /**
     * 素材图片上传
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        //检查参数
        if (multipartFile == null || multipartFile.getSize() == 0) {
            //参数无效
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //上传图片到minio
        //String filename = String.valueOf(System.currentTimeMillis());
        String filename = UUID.randomUUID().toString().replace("-","");
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = multipartFile.getOriginalFilename().substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("",filename + postfix, multipartFile.getInputStream());
            log.info("上传图片到minio中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");
        }
        //保存到数据库
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);
        //返回结果
        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * 素材列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmMaterialDto dto) {
        //检查分页参数
        dto.checkParam();
        //分页查询
        Page page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //是否收藏
        if (dto.getIsCollection() != null && dto.getIsCollection() == 1) {
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }
        //按照用户查询
        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId());
        //时间倒叙
        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);
        //结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 素材图片删除
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {
        //检查参数
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断当前素材有没有在使用
        List<WmNewsMaterial> list = new LambdaQueryChainWrapper<>(wmNewsMaterialMapper).eq(WmNewsMaterial::getMaterialId, id).list();
        if(list.size() == 0){
            //没有删除
            wmMaterialMapper.deleteById(id);
            return ResponseResult.okResult(200,"删除成功");
        }else {
            //有报错
            return ResponseResult.errorResult(400,"当前素材被引用，不能删除");
        }

    }


}
