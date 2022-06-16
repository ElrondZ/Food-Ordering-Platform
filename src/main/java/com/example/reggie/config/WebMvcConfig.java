package com.example.reggie.config;

import com.example.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*
    set static resource, Exp: redirect to backend index.html

    SpringBoot Default access its static resource to "public", "resources", "static"
    https://blog.csdn.net/u010358168/article/details/81205116
    @param registry
     */

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("start to config static resource");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/frontend/**").addResourceLocations("classpath:/frontend/");
    }

    /**
     * extend the mvc message converter
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info(">>>> extending the message converter >>>>");
        //construct MessageConverter object
        MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
        //setup object converter, use Jackson, JAVA data >> JSON
        mc.setObjectMapper(new JacksonObjectMapper());
        //add the message converter to mcv frame list
        converters.add(0, mc); // 0 is for priority, so it will use the message converter first.


    }
}
