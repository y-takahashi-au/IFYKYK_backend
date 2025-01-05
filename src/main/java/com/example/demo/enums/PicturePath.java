package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PicturePath {
  RESTAURANT("flat_house/");

  private final String path;

}
