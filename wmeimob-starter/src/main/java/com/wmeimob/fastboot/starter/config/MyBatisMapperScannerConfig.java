package com.wmeimob.fastboot.starter.config;

import com.wmeimob.fastboot.core.orm.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 配置mapperScannerConfigurer
 *
 * @author loafer
 */
@Configuration
public class MyBatisMapperScannerConfig {
    //指定统配扫描的包名
    // 统一扫描名字为.mapper的子包
    // 这就是一切项目的mapper 没有@mapper注释的原因了。
    private static final String[] BASE_PACKAGE = new String[]{"com.**.mapper", "org.**.mapper", "cn.**.mapper", "net.**.mapper", "me.**.mapper", "com.**.dao", "cn.**.dao", "org.**.dao", "net.**.dao", "me.**.dao", "com.**.repository", "cn.**.repository", "org.**.repository", "net.**.repository", "me.**.repository"};

    public MyBatisMapperScannerConfig() {
    }

    /**
     * 把要扫描的数组通配符 拼接成字符串
     *  例子： com.**.mapper,org.**.mapper.....
     * @return 拼好的包名字符串
     */
    private static String getBasePackage() {

        StringBuilder basePackageStr = new StringBuilder();
        String[] packages = BASE_PACKAGE;

        int len = packages.length;

        // 用forEach 更好
        for (String s : packages) {
            basePackageStr.append(s).append(",");
        }

        return basePackageStr.toString();
    }
    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(getBasePackage());
        Properties properties = new Properties();
        properties.setProperty("mappers", Mapper.class.getName());
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
