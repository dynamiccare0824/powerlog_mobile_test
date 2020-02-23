package test.powerlog.mobile.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("test.powerlog.mobile.springboot.web.dto.controller"))
                .paths(PathSelectors.any())//v1 pagackger를 controller 안에 따로 만들면 거기 있는 것만 가져올 수도 있음
                //이 때 코드는 PathSelectors.ant("/v1/**") 당연히 v1 앞은 basepackage 패키지가 들어감
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("앱 개발시 사용되는 서버 API에 대한 연동 문서입니다")
                .license("tmddusgood").licenseUrl("http://github.com/tmddusgood").version("1").build();
    }
}
