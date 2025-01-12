package com.ifykyk.service.helper;

import com.ifykyk.enums.Base64Enum;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageConvertHelper {

  public String toBase64(byte[] picture) {
    String base64str = Base64.getEncoder().encodeToString(picture);
    return Base64Enum.JPEG.getPrefix() + base64str;
  }

  public byte[] toJpegByte(String base64) {
    String base64ImageString = base64.replace(Base64Enum.JPEG.getPrefix(), "");
    return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64ImageString);
  }

}

