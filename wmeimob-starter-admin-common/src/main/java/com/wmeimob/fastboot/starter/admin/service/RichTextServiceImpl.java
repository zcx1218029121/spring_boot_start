package com.wmeimob.fastboot.starter.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.fastboot.core.exception.CustomException;
import com.wmeimob.fastboot.starter.common.entity.RichText;
import com.wmeimob.fastboot.starter.common.mapper.RichTextMapper;
import com.wmeimob.fastboot.starter.common.service.RichTextService;
import com.wmeimob.fastboot.util.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * service of richTextServiceImpl
 * @author loafer
 */
@Service("richTextServiceImpl")
public class RichTextServiceImpl implements RichTextService {
    @Resource
    private RichTextMapper richTextMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void add(RichText richText) {
        if (richText == null){
            throw  new CustomException("missing data id reference");
        }else {
            //clear content
            richText.setContent(filter(richText.getContent()));
            richText.setGmtCreate(new Date());
            // 插入数据
            richTextMapper.insert(richText);
            // 设置缓存
            this.stringRedisTemplate.opsForValue().set(String.format(RichTextService.CACHE_KEY_FORMAT, richText.getId()), JSONObject.toJSONString(richText));
        }
    }

    /**
     * del richText by id
     * step1. del rich in mysql
     * step2. del rich in redis
     * @param id id of del richText
     * @return sql result
     */
    @Override
    public int delete(Integer id) {
        int delResult = richTextMapper.deleteByPrimaryKey(id);
        if (delResult>0){
            stringRedisTemplate.delete(String.format(RichTextService.CACHE_KEY_FORMAT,id));
        }
        return delResult;
    }

    @Override
    public int update(RichText richText) {
        richText.setDataId((Integer)null);
        richText.setContent(this.filter(richText.getContent()));
        richText.setGmtModified(new Date());
        int result = this.richTextMapper.updateByPrimaryKeySelective(richText);
        if (result > 0) {
            this.stringRedisTemplate.delete(String.format(RichTextService.CACHE_KEY_FORMAT, richText.getId()));
        }

        return result;
    }

    /**
     * 防止script注入 去除文本中的 js 脚本标签
     * @param content the string of need filter js
     * @return  clear string
     */
    private String filter(String content) {
        if (!StringUtils.isEmpty(content)) {
            content = content.replace("<script>", "").replace("javascript", "").replace("$", "").replace("#", "").replace("--", "");
        }

        return content;
    }


}
