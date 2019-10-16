package com.zuovx.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zuoweixing
 * @date 2019-10-14 16:33
 */
@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
public class Swagger2Config {
	/**
	 * 添加摘要信息(Docket)
	 */
	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("标题：胡思乱想公司_书籍共享系统_接口文档")
						.description("描述：用于胡思乱想公司内部接口")
						.contact(new Contact("北漂小分组", null, null))
						.version("版本号:1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zuovx.book.controller"))
				.paths(PathSelectors.any())
				.build();
	}
}