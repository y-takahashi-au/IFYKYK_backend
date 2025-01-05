package com.example.demo.entity;

import com.example.demo.enums.AppliedStatus;
import com.example.demo.utils.CreateULID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Table(name = "flat_house_review")
@Entity
@Getter
@Setter
public class FlatHouseReview {

  @Id
  @Column(name = "id", length = 26, nullable = false)
  private String id = CreateULID.UUID();

  @Column(name = "flat_house_id", length = 26, nullable = false)
  private String flatHouseId;

  @Column(name = "rent", nullable = false)
  private int rent;

  @Column(name = "move_in")
  private LocalDate moveIn = LocalDate.now();

  @Column(name = "move_out")
  private LocalDate moveOut = LocalDate.now();

  @Column(name = "star", nullable = false)
  private int star;

  @Column(name = "review")
  private String review;

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private AppliedStatus status;

  @Column(name = "created_at")
  private Timestamp createdAt = Timestamp.from(Instant.now());

  @Column(name = "updated_at")
  private Timestamp updatedAt = Timestamp.from(Instant.now());

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "flat_house_review_id")
  private List<FlatHousePicture> flatHousePictures = new ArrayList<>();
}
