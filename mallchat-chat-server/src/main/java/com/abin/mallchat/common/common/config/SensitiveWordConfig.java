package com.abin.mallchat.common.common.config;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.algorithm.sensitiveWord.DFAFilter;
    import com.abin.mallchat.common.common.algorithm.sensitiveWord.SensitiveWordBs;
    import com.abin.mallchat.common.sensitive.MyWordFactory;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    @Configuration
@RequiredArgsConstructor
public class SensitiveWordConfig {    
    private final MyWordFactory myWordFactory;
    /**
     * 初始化引导类
     *
     * @return 初始化引导类
     * @since 1.0.0
     */
    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        return SensitiveWordBs.newInstance()
                .filterStrategy(DFAFilter.getInstance())
                .sensitiveWord(myWordFactory)
                .init();
    }

}