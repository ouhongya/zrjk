package com.zrjk.ZrjkSwagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestfulApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())  //设置apiInfo
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zrjk.MonsterZrjk")) //扫描controller存在的包
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger页面显示信息（没太大用）
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("知润健康")
                //创建人
                .contact(new Contact("DC", "www.baidu.com", "827807452@qq.com"))
                //版本号
                .version("999999")
                //描述
                .description("花花世界迷人眼")
                .build();
    }
}
