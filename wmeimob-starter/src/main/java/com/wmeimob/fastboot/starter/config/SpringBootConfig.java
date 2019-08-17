package com.wmeimob.fastboot.starter.config;

import com.wmeimob.fastboot.core.convert.DateConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加格式化器
 *
 */
@Configuration
public class SpringBootConfig implements WebMvcConfigurer {

    public SpringBootConfig() {
    }

    /**
     * 注册时间格式化工具
     * @param registry FormatterRegistry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConvert());
    }
}
