package com.ifykyk.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ifykyk.dto.AccountInfoResponse;
import com.ifykyk.dto.ApprovalAccountDto;
import com.ifykyk.entity.Account;
import com.ifykyk.entity.AccountInfo;
import com.ifykyk.enums.AccountRoll;
import com.ifykyk.enums.AppliedStatus;
import com.ifykyk.exception.LalinguaBussinessException;
import com.ifykyk.repository.AccountInfoRepository;
import com.ifykyk.repository.AccountRepository;
import com.ifykyk.service.helper.AccountHelper;
import com.ifykyk.utils.CreateRandomId;
import com.ifykyk.utils.CreateULID;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  private final AccountInfoRepository accountInfoRepository;

  private final PasswordEncoder passwordEncoder;

  private final AccountHelper accountHelper;

  @Transactional
  public Account createAccount() {
    var userId = "";
    do {
      userId = CreateRandomId.randomId();
    }
    while (accountHelper.existUserId(userId));
    var account = new Account();
    account.setId(CreateULID.UUID());
    account.setUserId(userId);
    account.setStatus(AppliedStatus.CREATED);
    accountRepository.save(account);
    return account;
  }

  @Transactional()
  public void applyAccount(String userId, String givenName, String familyName,
      String password) {
    var account = accountRepository.findByUserIdAndStatus(userId, AppliedStatus.CREATED)
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));
    if (!account.getUpdatedAt().before(Timestamp.from(Instant.now().plusSeconds(6000)))) {
      throw new LalinguaBussinessException("account creation timed out.");
    }
    account.setStatus(AppliedStatus.APPLIED);
    accountRepository.save(account);

    var accountInfo = new AccountInfo();
    accountInfo.setAccountId(account.getId());
    accountInfo.setRoll(AccountRoll.GUEST);
    accountInfo.setGivenName(givenName);
    accountInfo.setFamilyName(familyName);
    accountInfo.setPassword(passwordEncoder.encode(password));
    accountInfo.setRefreshToken("");
    accountInfo.setRefreshTokenIssueAt(Instant.now());
    accountInfoRepository.save(accountInfo);
  }

  @Transactional()
  public void approveAccount(ApprovalAccountDto approvalAccountDto) {
    var account = accountRepository.findByUserIdAndStatus(approvalAccountDto.getUserId(),
            AppliedStatus.APPLIED)
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));

    account.setStatus(AppliedStatus.APPROVED);
    accountRepository.save(account);
    var accountInfo = accountInfoRepository.findById(account.getId())
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));

    accountInfo.setGivenName(approvalAccountDto.getGivenName());
    accountInfo.setFamilyName(approvalAccountDto.getFamilyName());
    accountInfo.setRoll(AccountRoll.toEnum(approvalAccountDto.getRoll()));
    accountInfoRepository.save(accountInfo);
  }

  private String createToken(String userId) {
    return JWT.create().withClaim("userid", userId).sign(Algorithm.HMAC256("__secret__"));
  }


  public HttpHeaders createHttpHeaders(String userId) {
    var httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", createToken(userId));
    return httpHeaders;
  }

  public AccountInfoResponse getAccountInfo(String userId) {
    String accountId = accountRepository.findByUserIdAndStatusIn(userId,
            List.of(AppliedStatus.APPROVED, AppliedStatus.APPLIED))
        .map(Account::getId)
        .orElseThrow(() -> new LalinguaBussinessException("userId not found."));
    String roll = accountInfoRepository.findById(accountId)
        .map(accountInfo -> {
          return accountInfo.getRoll().toString();
        }).orElseThrow(() -> new LalinguaBussinessException("userId not found."));
    var accountInfoResponse = new AccountInfoResponse();
    accountInfoResponse.setAccountId(accountId);
    accountInfoResponse.setUserId(userId);
    accountInfoResponse.setRoll(roll);
    return accountInfoResponse;
  }

  @Transactional(readOnly = true)
  public ResponseEntity<AccountInfoResponse> getAccountInfoResponse(String userId) {
    var response = getAccountInfo(userId);
    var header = createHttpHeaders(userId);
    return new ResponseEntity<AccountInfoResponse>(response, header, HttpStatus.OK);
  }
}
