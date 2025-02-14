package com.rebox.config;

import com.rebox.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ReboxConfig
 * Package:com.rebox.config
 * Descriptionn:
 *
 * @Author wqh
 * @Create 2024/5/8 20:45
 * @Version 1.0
 */

//@Configuration注解表示该类是配置类，配置类可以对spring容器进行配置，等价于spring的xml配置，比如applicationContext.xml
//同时这个注解也具有@Component注解的功能，可以把当前类对象变成spring容器的bean对象
@Configuration
public class ReboxConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    /**
     * 在spring容器中配置了一个bean
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 在spring容器中配置了一个线程池的bean
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // Runtime.getRuntime().availableProcessors() 这个是拿到cpu的核心数据
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, //核心线程数，一般是给大多？ 一般是cpu核心数的2倍
                Runtime.getRuntime().availableProcessors() * 2, //最大线程数，往往和核心线程数值一样，这样效率是最好的
                60, //线程空闲多久之后，就可以销毁
                TimeUnit.SECONDS, //空闲时间单位
                new ArrayBlockingQueue<>(1024), //队列的大小，一般是给128,256,512,1024
                Executors.defaultThreadFactory(), //线程工厂，线程工厂是用来创建线程的
                new ThreadPoolExecutor.CallerRunsPolicy()); //线程池拒绝策略
    }

    /**
     * 覆盖该方法 （添加拦截器）
     *
     * <mvc:interceptor>
     *     <
     * </mvc:interceptor>
     *
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //InterceptorRegistry 拦截器的注册器
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/api/**") //要拦截哪些路径 /api/work、 /api/user/123
//                .excludePathPatterns("/api/login"); //不拦截哪些路径
//
//    }

}