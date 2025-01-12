package com.ifykyk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Base64Enum {
  JPEG("data:image/jpeg;base64,", ".jpeg");

  private final String prefix;
  private final String extension;

}
