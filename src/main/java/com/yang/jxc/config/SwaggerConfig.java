package com.yang.jxc.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 李博洋
 * @date 2025/5/27
 * swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi(Environment environment) {

        // 设置要显示swagger的环境
        Profiles of = Profiles.of( "dev","prod");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean b = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                .enable(b)
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                 .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.ygy.jxc.controller"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("商品进销存管理系统")
                //文档描述
                .description("接口说明")
                //服务条款URL
                .termsOfServiceUrl(" ")
                //版本号
                .version("1.0.0")
                .build();
    }
    //ui页面
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
