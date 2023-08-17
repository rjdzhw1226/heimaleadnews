package com.heima.seata.config;

import com.alibaba.druid.pool.DruidDataSource;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class DataSourcesProxyConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
    //创建代理数据源

    @Primary//@Primary标识必须配置在代码数据源上，否则本地事务失效
    @Bean
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    private MybatisPlusProperties properties;

    public DataSourcesProxyConfig(MybatisPlusProperties properties) {
        this.properties = properties;
    }

    //替换SqlSessionFactory的DataSource
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {

        // 这里必须用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSourceProxy);
        mybatisSqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());

        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));

        MybatisConfiguration configuration = this.properties.getConfiguration();
        if(configuration == null){
            configuration = new MybatisConfiguration();
        }
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);


        return mybatisSqlSessionFactoryBean;
    }
}
