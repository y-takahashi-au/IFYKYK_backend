package com.example.demo.controller;

import com.example.demo.dto.AccountInfoDto;
import com.example.demo.dto.AccountInfoResponse;
import com.example.demo.dto.ApprovalAccountDto;
import com.example.demo.dto.UserAuthentication;
import com.example.demo.exception.LalinguaBussinessException;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  private final DaoAuthenticationProvider daoAuthenticationProvider;

  @GetMapping("/authorize")
  public void authorize() {
  }

  @PutMapping("/create")
  public ResponseEntity<String> createAccount() {
    return new ResponseEntity<String>(accountService.createAccount().getUserId(), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AccountInfoResponse> login(@RequestBody UserAuthentication request) {
    try {
      // DaoAuthenticationProviderを用いた認証を行う
      daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
          request.getUserId(), request.getPassword()));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    try {
      return accountService.getAccountInfoResponse(request.getUserId());
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

  }

  @PutMapping("/apply")
  public ResponseEntity<String> applyAccount(@RequestBody AccountInfoDto request) {
    try {
      accountService.applyAccount(request.getUserId(), request.getGivenName(),
          request.getFamilyName(), request.getPassword());
      return new ResponseEntity<String>("やったね", HttpStatus.OK);
    } catch (UsernameNotFoundException e) {
      return new ResponseEntity<String>("無効な", HttpStatus.BAD_REQUEST);
    } catch (LalinguaBussinessException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/approve")
  public ResponseEntity<String> approveAccount(@RequestBody ApprovalAccountDto request) {
    try {
      accountService.approveAccount(request);
      return new ResponseEntity<String>("やったね", HttpStatus.OK);
    } catch (UsernameNotFoundException e) {
      return new ResponseEntity<String>("無効な", HttpStatus.BAD_REQUEST);
    }
  }
}
