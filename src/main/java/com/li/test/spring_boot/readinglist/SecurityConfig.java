package com.li.test.spring_boot.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//Profile是一种条件化配置，只有激活production配置文件才运行该bean
@Profile("production")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        //“/”的请求只有经过身份认证且拥有READER角色的用户才能访问
        .antMatchers("/**").access("hasRole('READER')")
        //剩余请求开放访问权限
//        .antMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")//设置登录表单的路径
        .failureUrl("/login?error=true");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定义自定义UserDetailsService
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
                return readerRepository.findOne(username);
            }
        });
    }
}
