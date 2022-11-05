package com.app.thingsilove.web.common.security;

import com.app.thingsilove.core.user.UserAuthRepository;
import com.app.thingsilove.core.user.UserRepository;
import com.app.thingsilove.core.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.InvalidSessionAccessDeniedHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final UserDetailService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://things-i-love.netlify.app","https://blog.ugosdevblog.com","http://localhost:8081"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT","OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers(HttpMethod.GET,"/").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/user").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/user/login").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/user/logout").permitAll()
                    .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider())
                .cors().configurationSource(new CorsConfig())
                .and()
                .csrf().disable()
                .formLogin()
                    .loginPage("/")
                    .defaultSuccessUrl("/api/user/login")
                    .passwordParameter("pwd")
                    .usernameParameter("email")
                    .loginProcessingUrl("/api/user/login")
                    .successForwardUrl("/api/user/login")
                .and()
                .logout()
                    .logoutSuccessUrl("/logoutSuccess")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/api/user/session-expired")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/api/user/session-expired");
//        InvalidSessionAccessDeniedHandler
        return http.build();
    }
}
