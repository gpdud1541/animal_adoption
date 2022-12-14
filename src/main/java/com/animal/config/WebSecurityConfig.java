package com.animal.config;

import com.animal.common.handler.LoginFailHandler;
import com.animal.common.handler.LoginSuccessHandler;
import com.animal.common.handler.LogoutSuccessHandler;
import com.animal.common.handler.WebAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.net.URI;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SecurityUserService securityUserService;

    @Autowired
    private WebAccessDeniedHandler webAccessDeniedHandler;

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .headers().frameOptions().sameOrigin()
                .headers().frameOptions()
                .disable().addHeaderWriter(
                new XFrameOptionsHeaderWriter(
                        new StaticAllowFromStrategy(URI.create("*"))
                ))
//                .sameOrigin()
                .and()
                .authorizeRequests()

//                .antMatchers("/", "/login", "join", "logout").permitAll()
//                .antMatchers("/compbooth/**", "/member/authenticate", "/member/findpwd", "/member/find", "/member/alter").permitAll()

//              .antMatchers("/", "/login", "/join", "/logout").permitAll() // ??????/?????? ???????????? ?????? ??????
/*
                .antMatchers("/websocket/**").permitAll()

                .antMatchers("/worker/**").access("hasRole('ROLE_WORKER')")

                .antMatchers("/index/main").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/index/**").authenticated()

                .antMatchers("/company/**").access("hasRole('ROLE_MASTER') or hasRole('ROLE_COMPANY')")
                .antMatchers("/ems/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_COMPANY')")
*/


                .antMatchers("/**").permitAll() // ?????? ????????? ??????/?????? ???????????? ?????? ?????? -> ?????? ???????????? ?????? ??????
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)// ??????????????? ?????? ( LoginSuccessHandler : ????????? ?????? ??? )
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());
        http
                .formLogin().loginPage("/login")
                .successForwardUrl("/")
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailHandler)
                .usernameParameter("account").passwordParameter("password") // Spring SecurityManager??? ???????????? username, password ??????
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                .and()
                .authenticationProvider(authenticationProvider())
                .csrf().disable();
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        // ????????? csrf disable


        //.logoutUrl("/logout").logoutSuccessUrl("/logout")
//        http.exceptionHandling().accessDeniedHandler(webAccessDeniedHandler) // ???????????? ????????? ?????? ??? ?????? Handler ( ?????? ?????? )
//                .and()
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static ??????????????? ?????? ?????? ????????? ?????? ??????
        web.ignoring().antMatchers("/static/**");
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
//    }


    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler("/");
        return loginSuccessHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler("/");
        return logoutSuccessHandler;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(securityUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
