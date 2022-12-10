package com.yinghuomars.microblog.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2022/11/8
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    private final Environment environment;

    private final SwaggerBasicProperties basicProperties;

    private final SwaggerSecurityProperties securityProperties;

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        log.debug("init default BeanPostProcessor");
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    @Bean
    public Docket docket() {
        log.debug("init docket[Docket]: [basic={}, security={}]", basicProperties, securityProperties);
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(basicProperties.getProtocols())
                .host(basicProperties.getHost())
                .groupName(basicProperties.getGroupName())
                .select();
        Docket docket = builder
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
        docket.pathMapping(basicProperties.getPathMapping()).genericModelSubstitutes(ResponseEntity.class);
        if (basicProperties.getEnableAuth()) {
            docket.securitySchemes(getSecuritySchemes()).securityContexts(getSecurityContexts());
        }
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(StringUtils.hasText(basicProperties.getTitle())
                        ? basicProperties.getTitle()
                        : environment.getProperty("spring.application.name")
                )
                .description(StringUtils.hasText(basicProperties.getDescription())
                        ? basicProperties.getDescription()
                        : environment.getProperty("spring.application.name"))
                .version(basicProperties.getVersion())
                .contact(new Contact(basicProperties.getContactName(),
                        basicProperties.getContactUrl(),
                        basicProperties.getContactEmail()))
                .termsOfServiceUrl(basicProperties.getTermsOfServiceUrl())
                .license(basicProperties.getLicense())
                .licenseUrl(basicProperties.getLicenseUrl())
                .build();
    }

    private List<SecurityScheme> getSecuritySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.addAll(securityProperties.getApiKeys());
        securitySchemes.addAll(securityProperties.getOAuths());
        if (CollectionUtils.isEmpty(securitySchemes)) {
            securitySchemes.add(getDefaultSecurityScheme());
        }
        return securitySchemes;
    }

    private List<SecurityContext> getSecurityContexts() {
        ArrayList<SecurityContext> contexts = new ArrayList<>(securityProperties.getContexts());
        if (CollectionUtils.isEmpty(contexts)) {
            contexts.add(getDefaultSecurityContext());
        }
        return contexts;
    }

    private static SecurityScheme getDefaultSecurityScheme() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
    }

    private static SecurityContext getDefaultSecurityContext() {
        return SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, new AuthorizationScope[]{new AuthorizationScope("global", "global")}))).build();
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "yinghuomars.swagger.basic")
    public static class SwaggerBasicProperties {
        /**
         * 标题
         */
        private String title;

        /**
         * 描述
         */
        private String description;

        /**
         * 组
         */
        private String groupName = "default";

        /**
         * 版本
         */
        private String version = "1.0.0-SNAPSHOT";

        /**
         * 作者名
         */
        private String contactName = "YHMARS";

        /**
         * 作者url
         */
        private String contactUrl = "https://www.yinghuomars.com";

        /**
         * 作者邮箱
         */
        private String contactEmail = "yhm906219418@icloud.com";

        /**
         * 许可证
         */
        private String license = "Apache License 2.0";

        /**
         * 许可证url
         */
        private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";

        /**
         * 服务条款url
         */
        private String termsOfServiceUrl;

        /**
         * 是否开启鉴权
         */
        private Boolean enableAuth = Boolean.FALSE;

        /**
         * 支持的协议
         */
        private Set<String> protocols = Collections.singleton("http");

        /**
         * host
         */
        private String host = "http://127.0.0.1";

        private String pathMapping = "/";
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "yinghuomars.swagger.security")
    public static class SwaggerSecurityProperties {
        private List<ApiKeyBean> apiKeys = new ArrayList<>();
        private List<OAuthBean> oAuths = new ArrayList<>();
        private List<SecurityContextBean> contexts = new ArrayList<>();

        public List<ApiKey> getApiKeys() {
            return apiKeys.stream().map(item -> new ApiKey(item.getName(), item.getKeyName(), item.getPassAs())).collect(Collectors.toList());
        }

        public List<OAuth> getOAuths() {
            return oAuths.stream().map(item -> new OAuth(item.getName(), item.getAuthorizationScopes(), item.getGrantTypes())).collect(Collectors.toList());
        }

        public List<SecurityContext> getContexts() {
            return this.contexts.stream().map(item -> SecurityContext.builder().securityReferences(item.getReferences()).build()).collect(Collectors.toList());
        }

        @Data
        public static class ApiKeyBean {
            private String name;
            private String keyName;
            private String passAs;
        }

        @Data
        public static class OAuthBean {
            private String name;
            private List<AuthorizationScopeBean> authorizationScopes = new ArrayList<>();
            private List<GrantTypeBean> grantTypes = new ArrayList<>();

            @Data
            public static class GrantTypeBean {
                private String type;
            }

            public List<AuthorizationScope> getAuthorizationScopes() {
                return authorizationScopes.stream().map(item -> new AuthorizationScope(item.getScope(), item.getDescription())).collect(Collectors.toList());
            }

            public List<GrantType> getGrantTypes() {
                return this.grantTypes.stream().map(item -> new GrantType(item.getType())).collect(Collectors.toList());
            }
        }

        @Data
        public static class AuthorizationScopeBean {
            private String scope;
            private String description;
        }

        @Data
        public static class SecurityContextBean {
            private List<SecurityReferenceBean> references = new ArrayList<>();

            @Data
            public static class SecurityReferenceBean {
                private String reference;
                private List<AuthorizationScope> scopes = new ArrayList<>();
            }

            public List<SecurityReference> getReferences() {
                return this.references.stream().map(item -> new SecurityReference(item.getReference(), item.getScopes().toArray(AuthorizationScope[]::new))).collect(Collectors.toList());
            }
        }
    }

}
