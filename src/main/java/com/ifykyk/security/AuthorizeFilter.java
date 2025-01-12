package com.ifykyk.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {

  private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/account/login");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (!matcher.matches(request)) {
      // headersのkeyを指定してトークンを取得します
      String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
      System.out.println(
          "------------------" + "authorization:" + authorization + "------------------");
      if (authorization == null || !authorization.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      // tokenの検証と認証
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("__secret__")).build()
          .verify(authorization.substring(7));
      // usernameの取得
      String userId = decodedJWT.getClaim("userid").toString();
      // ログイン状態の設定
      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList()));
    }
    filterChain.doFilter(request, response);
  }
}
