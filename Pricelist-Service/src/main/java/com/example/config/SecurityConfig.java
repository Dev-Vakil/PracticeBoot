package com.example.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import feign.RequestInterceptor;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerConfigurerAdapter{
	
	private final ResourceServerProperties resourceServerProperties;
	
	private final OAuth2ClientContext oAuth2ClientContext;
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Value("#{'${security.allowedOrigin}'.split(',')}")
	private List<String> allowedOrigin;
	
	@Autowired
	public SecurityConfig(ResourceServerProperties resourceServerProperties, OAuth2ClientContext oAuth2ClientContext) {
		this.resourceServerProperties = resourceServerProperties;
		this.oAuth2ClientContext = oAuth2ClientContext;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}
	
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, clientCredentialsResourceDetails());
	}
	
	@Bean
	public RestOperations restTemplate(OAuth2ClientContext oAuth2ClientContext) {
		return new OAuth2RestTemplate(clientCredentialsResourceDetails(), oAuth2ClientContext);
	}
	
	@Bean
	@Primary
	public ResourceServerTokenServices resourceServerTokenServices() {
		return new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.cors().and().authorizeRequests()
		.antMatchers("/**")
		.permitAll()
		.anyRequest().authenticated();
		http.addFilterAfter(securityFilter, AuthorizationFilter.class);		
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(allowedOrigin);
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return bean;
	}
}
