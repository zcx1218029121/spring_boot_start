package com.wmeimob.fastboot.starter.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.fastboot.core.exception.CustomException;
import com.wmeimob.fastboot.core.rest.RestResult;
import com.wmeimob.fastboot.starter.common.entity.RichText;
import com.wmeimob.fastboot.starter.common.service.RichTextService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * richTextController
 * @author  loafer
 */
@RestController
@RequestMapping({"rich-text"})
public class RichTextController {
    //inject richTextService by bean name
    @Resource(
            name = "richTextServiceImpl"
    )
    private RichTextService richTextService;
    //inject richTextService by bean name (common 包下的 通用服务)
    @Resource(
            name = "richTextCommonServiceImpl"
    )
    private RichTextService richTextCommonServiceImpl;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public RichTextController(){

    }

    @DeleteMapping({"{id}"})
    public RestResult deleteById(@PathVariable("id") Integer id) {
        RichText richText = this.richTextCommonServiceImpl.securityGet(id, (dataId) -> {
            return true;
        });
        if (richText == null) {
            return RestResult.fail("您无权进行此操作");
        } else {
            return this.richTextService.delete(id) > 0 ? RestResult.success() : RestResult.fail();
        }
    }

    @PostMapping
    public RestResult add(@RequestBody RichText richText) {
        this.checkInput(richText);
        this.richTextService.add(richText);
        // java 8之后 不在为hash map 分配6位的初始大小了，这里的优化实际上不再有用
        Map<String, Integer> map = new HashMap<>(1);
        map.put("id", richText.getId());
        return RestResult.success(map);
    }

    @PutMapping({"{id}"})
    public RestResult update(@PathVariable("id") Integer id, @RequestBody RichText richText) {
        this.checkInput(richText);
        RichText query = this.richTextCommonServiceImpl.securityGet(id, (dataId) -> {
            return true;
        });
        if (query == null) {
            return RestResult.fail("您无权进行此操作");
        } else {
            return this.richTextService.update(richText) > 0 ? RestResult.success() : RestResult.fail();
        }
    }

    private void checkInput(RichText richText) {
        if (richText.getDataId() == null) {
            throw new CustomException("丢失数据引用");
        }
    }

    @GetMapping({"preview"})
    public String getPreview(String id) {
        RichText richText = (RichText) JSONObject.parseObject((String)this.stringRedisTemplate.opsForValue().get(String.format(RichTextService.CACHE_KEY_FORMAT, id)), RichText.class);
        return richText == null ? "" : richText.getContent();
    }

    @PostMapping({"preview"})
    public String addPreview(RichText richText) {
        String id = UUID.randomUUID().toString();
        this.stringRedisTemplate.opsForValue().set(String.format(RichTextService.CACHE_KEY_FORMAT, id), JSONObject.toJSONString(richText), 1L, TimeUnit.HOURS);
        return id;
    }
}
