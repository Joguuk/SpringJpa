package com.jjozerg.jkhr.config;

import com.jjozerg.jkhr.config.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName : com.jjozerg.jkhr.config
 * fileName : WebConfig
 * author : joguk
 * date : 2022/02/12
 * description : Web Config 설정 class
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/12 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * loginInterceptor 설정
     *
     * @author joguk
     * @date 2022/02/12 5:39 오후
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/member/v1/login", "/member/v1/members");
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}
