package com.uncodigo.clientes.app.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/clients", "/api/clients/page/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/clients/**", "/api/invoices/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients/**", "/api/invoices/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/clients/**", "/api/invoices/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/clients/**", "/api/invoices/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/api/clients/{id}").hasAnyRole("USER", "SELLER", "ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/clients/photo/upload").hasAnyRole("SELLER", "ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/clsients/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/clients").hasAnyRole("ADMIN", "SELLER")
//                .antMatchers(HttpMethod.GET,"/api/countries/{term}").hasAnyRole("ADMIN","SELLER")
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configCors = new CorsConfiguration();
        configCors.setAllowedOrigins(List.of("http://localhost:4200"));
        configCors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configCors.setAllowCredentials(true);
        configCors.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configCors);

        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
