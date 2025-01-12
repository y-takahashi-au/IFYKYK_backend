package com.ifykyk.service;

import com.ifykyk.entity.Account;
import com.ifykyk.enums.AppliedStatus;
import com.ifykyk.exception.LalinguaBussinessException;
import com.ifykyk.repository.AccountInfoRepository;
import com.ifykyk.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

  private final AccountRepository accountRepository;

  private final AccountInfoRepository accountInfoRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    String accountId = accountRepository.findByUserIdAndStatusIn(userId,
            List.of(AppliedStatus.APPROVED, AppliedStatus.APPLIED))
        .map(Account::getId)
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));
    return accountInfoRepository.findById(accountId)
        .map(accountInfo -> User.withUsername(accountInfo.getAccountId())
            .password(accountInfo.getPassword())
            .roles(accountInfo.getRoll().toString())
            .build())
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));
  }
}
