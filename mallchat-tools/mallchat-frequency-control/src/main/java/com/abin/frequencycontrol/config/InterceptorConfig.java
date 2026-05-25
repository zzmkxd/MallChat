package com.abin.frequencycontrol.config;
    import lombok.RequiredArgsConstructor;
    import com.abin.frequencycontrol.intecepter.CollectorInterceptor;
    import com.abin.frequencycontrol.intecepter.TokenInterceptor;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    /**
 * 配置所有拦截器
 */
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {    
    private final TokenInterceptor tokenInterceptor;
    private final CollectorInterceptor collectorInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**");
    registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
    }
}
