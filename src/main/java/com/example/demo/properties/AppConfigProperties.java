package com.example.demo.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {

  private String appName;
}