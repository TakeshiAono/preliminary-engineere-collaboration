package com.api.EngineerCollabo;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserService userService;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .formLogin(login -> login
          .loginProcessingUrl("/login") //このurlにpostでリクエストされると認証処理が開始される
          .loginPage("/routing/index") // vueのログインページのurlを記載する
          .defaultSuccessUrl("https://github.com/TakeshiAono/preliminary-engineere-collaboration") //ログイン成功した時にリダイレクトさせるurl
          // .failureUrl("/routing/index")// vueのログインページのurlを記載する
          .usernameParameter("username")
          .passwordParameter("password")
          .permitAll()
        )
        .csrf().disable() // APIサーバーの場合は必ず必要
        .authorizeHttpRequests((requests) -> requests
          .requestMatchers( "/routing/index").permitAll() //loginページは全員認証不要で許可される
          .anyRequest().authenticated() // 他のリクエストには認証が必要となる
        );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider ccc() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }
}