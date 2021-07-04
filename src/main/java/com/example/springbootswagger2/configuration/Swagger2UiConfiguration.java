package com.example.springbootswagger2.configuration;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2UiConfiguration extends WebMvcConfigurerAdapter  {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                // 绑定swagger-ui的展示内容
				.apiInfo(apiInfo())
				.select()
                // 绑定扫描的类
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

    /**
     * 指定swagger2 ui的显示格式
     * @return
     */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title("swagger和springBoot整合演示")
                .description("swagger的API文档演示效果")
				.version("1.0")
                .build();
	}

}
