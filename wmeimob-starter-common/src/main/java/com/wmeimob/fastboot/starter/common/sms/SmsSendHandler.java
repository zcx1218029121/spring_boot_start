package com.wmeimob.fastboot.starter.common.sms;

import com.wmeimob.fastboot.autoconfigure.sms.SmsPreSendHandler;
import com.wmeimob.fastboot.autoconfigure.sms.SmsValidator;
import com.wmeimob.fastboot.autoconfigure.sms.aliyun.AliyunSmsProperties;
import com.wmeimob.fastboot.core.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class SmsSendHandler implements SmsPreSendHandler, SmsValidator {


    private static final Logger log = LoggerFactory.getLogger(SmsSendHandler.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public SmsSendHandler() {
    }

    public void process(String mobile, String code, String scene, AliyunSmsProperties aliyunSmsProperties) {
        AliyunSmsProperties.SignTemplate signTemplate = (AliyunSmsProperties.SignTemplate) aliyunSmsProperties.getConfigs().get(scene);
        boolean check = this.stringRedisTemplate.opsForValue().setIfAbsent("sms:" + mobile + ":lock", "lock");
        if (!check) {
            String errMsg = "触发[" + ((AliyunSmsProperties.SignTemplate) aliyunSmsProperties.getConfigs().get(scene)).getTimeout() + "s]流控";
            log.error(errMsg);
            throw new CustomException(errMsg);
        } else {
            this.stringRedisTemplate.expire("sms:" + mobile + ":lock", (long) ((AliyunSmsProperties.SignTemplate) aliyunSmsProperties.getConfigs().get(scene)).getTimeout(), TimeUnit.SECONDS);
            this.stringRedisTemplate.opsForValue().set("sms:" + mobile + ":" + scene + ":code", code, (long) signTemplate.getExpire(), TimeUnit.SECONDS);
        }
    }

    public boolean valid(String mobile, String code, String scene) {
        String cache = (String) this.stringRedisTemplate.opsForValue().get("sms:" + mobile + ":" + scene + ":code");
        boolean result = Objects.equals(code, cache);
        if (result && code != null) {
            this.stringRedisTemplate.delete("sms:" + mobile + ":" + scene + ":code");
            return result;
        } else {
            return false;
        }
    }
}
