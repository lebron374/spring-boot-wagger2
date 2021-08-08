package com.example.springbootswagger2.configuration;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Profile({"dev", "test", "pre", "prod"})
public class Swagger2UiConfiguration extends WebMvcConfigurerAdapter  {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                // 绑定swagger-ui的展示内容
				.apiInfo(apiInfo())
				.select()
                // 绑定扫描的类
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
				.build();
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}


	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> list = new ArrayList<>();
		list.add(new BasicAuth("basicAuth"));
		list.add(new ApiKey("write_token","write_token","header"));
		list.add(new ApiKey("read_token","read_token","query"));

		return list;
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build()
		);
	}

}
