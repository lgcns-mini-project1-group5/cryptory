package com.cryptory.be.global.config;

import com.cryptory.be.global.util.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseDir = FileUtils.getBaseDir();

        registry.addResourceHandler("/attach/files/**")
                .addResourceLocations("file://" + baseDir + "/");

    }
}
