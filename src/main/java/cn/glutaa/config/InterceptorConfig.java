package cn.glutaa.config;

import cn.glutaa.interceptor.AuthorizationInterceptor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "filtersetting")
public class InterceptorConfig implements WebMvcConfigurer {

    //值从yml自定义Bean中注入
    private List<String> filterUrl;//拦截的url
    private List<String> passUrl;//不拦截的url

    @Bean
    public AuthorizationInterceptor getAuthorizationInterceptor(){
        return new AuthorizationInterceptor();
    }//让拦截器执行的时候实例化拦截器Bean，在拦截器配置类里面先实例化拦截器，然后再获取

    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(getAuthorizationInterceptor()).addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns(passUrl);
    }
}
