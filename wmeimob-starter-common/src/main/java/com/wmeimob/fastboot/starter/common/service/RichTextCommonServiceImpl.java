package com.wmeimob.fastboot.starter.common.service;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.fastboot.starter.common.entity.RichText;
import com.wmeimob.fastboot.starter.common.mapper.RichTextMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

import javax.annotation.Resource;

/**
 * RichTextService
 *
 * @author loafer
 */
@Service("richTextCommonServiceImpl")
public class RichTextCommonServiceImpl implements RichTextService {
    @Resource
    private RichTextMapper richTextMapper;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public RichTextCommonServiceImpl() {
    }

    /**
     * 二级缓存
     * 双重检查锁
     * <p>
     * 因为锁的对象是key.intern() 指的key 在常量池中的对象
     * 因为加的锁是 key的对象
     * 当多个线程持有相同的key对象 来初始化 richText对象的时候 上锁 排队
     * 当多个线程持有不同的key对象 来初始化 richText对象的时候 不排队
     *
     * @param id 查询id
     * @return r 查询的RichText对象
     */
    @Override
    public RichText findById(Integer id) {
        //这里设计出来就该是这样 可能是编译过程中先加载到常量池了
        //generator key
        String key = String.format(CACHE_KEY_FORMAT, id);

        RichText richText = JSONObject
                .parseObject(this
                        .stringRedisTemplate
                        .opsForValue()
                        .get(key), RichText.class);

        if (richText == null) {
            synchronized (key.intern()) {
                //singleton rickText
                richText = JSONObject.parseObject(this.stringRedisTemplate.opsForValue().get(key), RichText.class);
                if (richText == null) {
                    richText = this.richTextMapper.selectByPrimaryKey(id);
                    this.stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(richText));
                }
            }

        }
        return richText;
    }

    /**
     * @param id                  id
     * @param richTextAuthChecker 检查回调 判断富文本对象是否可以返回
     * @return richText           对象 无效的时候返回null
     */
    @Nullable
    @Override
    public RichText securityGet(@NonNull Integer id, RichTextAuthChecker richTextAuthChecker) {
        if (id == 0) {
            return new RichText();
        } else {
            RichText richText = this.findById(id);
            if (richText != null && richText.getDataId() != null) {
                boolean validResult = richTextAuthChecker.assertHasAuth(id);
                return validResult ? richText : null;
            } else {
                return richText;
            }
        }
    }


}
