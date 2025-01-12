package com.ifykyk.entity;

import com.ifykyk.utils.CreateULID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "flat_house_review_picture")
@Entity
@Getter
@Setter
public class FlatHousePicture {

  @Id
  @Column(name = "id", length = 26, nullable = false)
  private String id = CreateULID.UUID();

  @Column(name = "flat_house_review_id", length = 26, nullable = false)
  private String flatHouseReviewId;

  @Column(name = "picture_path", nullable = false)
  private String picturePath;
}
