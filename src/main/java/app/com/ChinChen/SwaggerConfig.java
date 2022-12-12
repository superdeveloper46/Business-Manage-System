// package app.com.ChinChen;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;

// @Configuration
// @EnableWebMvc

// public class SwaggerConfig implements WebMvcConfigurer {
//     @Bean
//     public Docket customDocket(){
//         return new Docket(DocumentationType.SWAGGER_2)
//                 .apiInfo(apiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors
//                         .basePackage("app.com.ChinChen.controller"))
//                 .paths(PathSelectors.any()).build();
//     }

//     private ApiInfo apiInfo(){
//         return new ApiInfoBuilder()
//                 .title("ChinChen API")
//                 .description("Success")
//                 .version("1.0.0")
//                 .build();
//     }
// }