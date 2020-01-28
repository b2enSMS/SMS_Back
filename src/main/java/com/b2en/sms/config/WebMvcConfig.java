package com.b2en.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
	   registry.addMapping("/**")
	   .allowedOrigins("http://localhost:3000")
	   .allowedMethods(HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(), HttpMethod.PATCH.name())
	   .maxAge(3600L);
   }
}