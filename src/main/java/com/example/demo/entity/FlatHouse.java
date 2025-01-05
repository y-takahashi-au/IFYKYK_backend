package com.example.demo.entity;

import com.example.demo.utils.CreateULID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Table(name = "flat_house")
@Entity
@Getter
@Setter
public class FlatHouse {

  @Id
  @Column(name = "id", length = 26, nullable = false)
  private String id = CreateULID.UUID();

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "created_at")
  private Timestamp createdAt = Timestamp.from(Instant.now());

  @Column(name = "updated_at")
  private Timestamp updatedAt = Timestamp.from(Instant.now());

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "flat_house_id")
  private List<FlatHouseReview> flatHouseReviews = new ArrayList<>();
}
