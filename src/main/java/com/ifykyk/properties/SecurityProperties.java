package com.ifykyk.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {

  private Access access;
  private Refresh refresh;

  @Getter
  @Setter
  public static class Access {

    private String key;
    private long expiration;
  }

  @Getter
  @Setter
  public static class Refresh {

    private long expiration;
    private int length;
  }

}
