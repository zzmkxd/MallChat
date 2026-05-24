package com.abin.mallchat.common.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mallchatOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MallChat API")
                        .description("MallChat 电商即时通讯系统接口文档")
                        .version("1.0")
                        .contact(new Contact()
                                .name("abin")
                                .url("https://github.com/zongzibinbin")));
    }
}
