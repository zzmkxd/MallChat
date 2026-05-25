package com.abin.mallchat.common.common.config;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.intecepter.BlackInterceptor;
    import com.abin.mallchat.common.common.intecepter.CollectorInterceptor;
    import com.abin.mallchat.common.common.intecepter.TokenInterceptor;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    /**
 * Description: 配置所有拦截器
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-04-05
 */
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {    
    private final TokenInterceptor tokenInterceptor;
    private final CollectorInterceptor collectorInterceptor;
    private final BlackInterceptor blackInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**");
    registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
    registry.addInterceptor(blackInterceptor)
                .addPathPatterns("/capi/**");
    }
}
