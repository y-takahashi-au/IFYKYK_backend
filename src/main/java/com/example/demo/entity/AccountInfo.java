package com.example.demo.entity;

import com.example.demo.enums.AccountRoll;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Table(name = "account_info")
@Entity
@Getter
@Setter
public class AccountInfo {

  @Id
  @Column(name = "account_id", length = 26, nullable = false)
  private String accountId;

  @Column(name = "roll", nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountRoll roll;

  @Column(name = "given_name", length = 50, nullable = false)
  private String givenName;

  @Column(name = "family_name", length = 50, nullable = false)
  private String familyName;

  @Column(name = "password", length = 100, nullable = false)
  private String password;

  @Column(name = "refresh_token", length = 100, nullable = false)
  private String refreshToken;

  @Column(name = "refresh_token_issue_at", nullable = false)
  private Instant refreshTokenIssueAt;
}
