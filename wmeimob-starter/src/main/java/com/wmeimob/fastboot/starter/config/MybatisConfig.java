package com.wmeimob.fastboot.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.wmeimob.fastboot.autoconfigure.mybatis.MyBatisProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis的相关配置
 * 事务管理
 *
 *
 * @author loafer
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    private DruidDataSource dataSource;

    //MyBatis配置信息继承tk插件的MybatisProperties
    //在tk原有读取注解的继承上添加了默认扫描的包 对tk包进行了简单的扩展
    @Resource
    private MyBatisProperties myBatisProperties;

    //该配置信息类为spring原生的读取属性类
    //源码并不多
    @Resource
    private DataSourceProperties dataSourceProperties;

    // 规范
    MybatisConfig() {
    }


    /**
     * 装配德鲁伊连接池
     *
     * @return dataSource德鲁伊连接池
     */
    @Bean(
            destroyMethod = "close"
            //destroy-method="close"的作用是当数据库连接不使用的时候,
            // 就把该连接重新放到数据池中,方便下次使用调用.
    )
    @ConfigurationProperties(
            prefix = "spring.datasource.druid"
    )
    public synchronized DruidDataSource getDataSource() {
        if (this.dataSource == null) {
            this.dataSource = new DruidDataSource();
            this.dataSource.setUsername(this.dataSourceProperties.getUsername());
            this.dataSource.setPassword(this.dataSourceProperties.getPassword());
            this.dataSource.setUrl(this.dataSourceProperties.getUrl());
            this.dataSource.setDriverClassName(this.dataSourceProperties.getDriverClassName());
        }

        return this.dataSource;
    }

    /**
     * 1.对Mybatis的配置通过
     * sqlSessionFactoryBean.setMapperLocations
     * 来配置MapperLocations
     * 2. 配置pagerHelp 分页插件
     * 3. 把pagerHelp 插件手动加到 mybatis 中
     * @return sqlSessionFactoryBean
     * @throws Exception
     */
    @Bean(
            name = {"sqlSessionFactory"}
    )
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        logger.info("[ config SqlSessionFactoryBean ]");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.getDataSource());
        bean.setTypeAliasesPackage(this.myBatisProperties.getTypeAliasesPackage());

        //配置 pagerHelper
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //把分页插件配置到bean里 Mybatis设置的方法（pagerHelper 实现了 ibatis的插件接口 ）
        bean.setPlugins(new Interceptor[]{pageHelper});

        //
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        List<org.springframework.core.io.Resource> mapperLocations = new ArrayList<>();

        String[] locationArr = this.myBatisProperties.getMapperLocations();

        for (String s : locationArr) {
            mapperLocations.addAll(new ArrayList(Arrays.asList(resolver.getResources(s))));
        }

        bean.setMapperLocations(mapperLocations.toArray(new org.springframework.core.io.Resource[mapperLocations.size()]));
        return bean.getObject();

    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.getDataSource());
    }

}
