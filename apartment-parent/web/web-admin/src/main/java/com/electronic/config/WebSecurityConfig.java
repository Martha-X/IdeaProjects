package com.electronic.config;

import com.electronic.handler.CustomAccessDenyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启controller中方法的权限控制
//@EnableWebSecurity是开启SpringSecurity的默认行为
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许iframe显示
        http.headers().frameOptions().sameOrigin();
        // 登录设置
        http.authorizeRequests()
                // 静态资源和登录页不需要认证
                .antMatchers("/static/**", "/login").permitAll()
                // 其他任何请求都需要认证
                .anyRequest().authenticated()
                // 配置自定义登录页
                .and().formLogin().loginPage("/login")
                // 登录成功后去往的地址
                .defaultSuccessUrl("/")
                // 注销的路径
                .and().logout().logoutUrl("/logout")
                // 注销后去往的地址
                .logoutSuccessUrl("/login")
                // 关闭csrf验证
                .and().csrf().disable();
        // 设置未授权时的处理规则
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDenyHandler());
    }

    // 创建一个密码加密器放到IOC容器中,加密时使用啥加密器就放啥加密器到容器中
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
