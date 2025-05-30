package com.yang.jxc.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李博洋
 * @date 2025/5/27
 * 分页配置
 */
@Configuration
public class PageHelperConfig {
    @Bean
    public Interceptor[] plugins() {
        return new Interceptor[]{new PageInterceptor()};
    }
}
