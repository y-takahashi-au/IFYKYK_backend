package com.example.demo.security;


import com.example.demo.service.LoginUserDetailsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthorizeFilter authenticationFilter;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        List.of("http://127.0.0.1:5173", "http://localhost:5173"));
    configuration.setAllowedMethods(
        List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name()));
    configuration.setExposedHeaders(List.of("*", "Authorization"));
    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control",
        "Content-Type", "Access-Control-Allow-Headers", HttpHeaders.AUTHORIZATION));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(c -> c.configurationSource(corsConfigurationSource())).exceptionHandling(
            customizer -> customizer.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        //.csrf(AbstractHttpConfigurer::disable)
        .csrf(csrf -> csrf.ignoringRequestMatchers("/account/**", "/flat_house/**")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))

        .sessionManagement(customizer ->
            customizer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        // .formLogin(login -> login // フォーム認証を使う
        // .permitAll()) // フォーム認証画面は認証不要
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/account/login")
            .permitAll() // トップページは認証不要
            .requestMatchers("/account/create")
            .permitAll() // トップページは認証不要
            .requestMatchers("/account/apply")
            .permitAll() // トップページは認証不要
            .requestMatchers("/test")
            .permitAll() // トップページは認証不要
            .anyRequest().authenticated() // 他のURLはログイン後アクセス可能
        );
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(
      LoginUserDetailsService loginUserDetailsService) {

    System.out.println(
        "------------------" + "loginUserDetailsService:" + loginUserDetailsService.toString()
            + "------------------");
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(loginUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
