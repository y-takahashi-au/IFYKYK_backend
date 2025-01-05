package com.example.demo.service.helper;

import com.example.demo.enums.AppliedStatus;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountHelper {

  private final AccountRepository accountRepository;

  @Transactional(readOnly = true)
  public boolean existUserId(String userId) {
    return accountRepository.existsAccountByUserId(userId);
  }

  @Transactional(readOnly = true)
  public boolean existUserIdAndCreated(String userId) {
    return accountRepository.existsAccountByUserIdAndStatus(userId, AppliedStatus.CREATED);
  }

  @Transactional(readOnly = true)
  public boolean existUserIdAndApplied(String userId) {
    return accountRepository.existsAccountByUserIdAndStatus(userId, AppliedStatus.APPLIED);
  }

  @Transactional(readOnly = true)
  public boolean existUserIdAndApproved(String userId) {
    return accountRepository.existsAccountByUserIdAndStatus(userId, AppliedStatus.APPROVED);
  }
}
