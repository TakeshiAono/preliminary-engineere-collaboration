package com.api.EngineerCollabo.config;


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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import com.api.EngineerCollabo.services.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserService userService;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String jwtSecret;

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = jwtSecret.getBytes(); // シークレットキーをバイト配列に変換
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256"); // SecretKeySpecを使用してSecretKeyを作成
        return NimbusJwtDecoder.withSecretKey(secretKey).build(); // SecretKeyを渡してデコーダーを作成
    }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 設定
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/login").permitAll() // `/login` エンドポイントを認証不要にする
            .requestMatchers("/authenticate", "/register").permitAll() // 認証および登録エンドポイントを公開
            .requestMatchers("/auth/check").permitAll()    // チェックエンドポイントも許可
            .requestMatchers("/auth/refresh").permitAll()  // リフレッシュトークンのエンドポイントを許可
            .anyRequest().authenticated() // 他のリクエストには認証が必要となる
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT認証なので､セッション管理をステートレスに設定
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .bearerTokenResolver(request -> {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("access_token".equals(cookie.getName())) {
                            return cookie.getValue();
                        }
                    }
                }
                return null;
            })
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        )
        .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessHandler((request, response, authentication) -> {
                // クッキーの削除
                ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0)
                    .build();
                
                ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0)
                    .build();

                response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
                response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
                response.setStatus(HttpStatus.OK.value());
            })
            .invalidateHttpSession(true)
            .clearAuthentication(true);

        return http.build();
    }



    // CORS 設定用の Bean
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // クライアントのURL
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedHeader("*"); // 許可するヘッダー
        configuration.setAllowCredentials(true); // 認証情報を許可

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // カスタム JWT 認証コンバータ
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // 必要に応じてカスタマイズ
        return converter;
    }
};
