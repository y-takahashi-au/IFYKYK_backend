package com.ifykyk.entity;

import com.ifykyk.enums.AppliedStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Table(name = "account")
@Entity
@Getter
@Setter
public class Account {

  @Id
  @Column(name = "id", length = 26, nullable = false)
  private String id;

  @Column(name = "user_id", length = 8, nullable = false)
  private String userId;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private AppliedStatus status;

  @Column(name = "created_at")
  private Timestamp createdAt = Timestamp.from(Instant.now());

  @Column(name = "updated_at")
  private Timestamp updatedAt = Timestamp.from(Instant.now());
}
