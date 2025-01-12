package com.ifykyk.controller;

import com.ifykyk.dto.FlatHouseConditionRequest;
import com.ifykyk.dto.FlatHouseDetailResponse;
import com.ifykyk.dto.FlatHouseEditDto;
import com.ifykyk.dto.FlatHouseListResponse;
import com.ifykyk.exception.LalinguaBussinessException;
import com.ifykyk.service.FlatHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flat_house")
@RequiredArgsConstructor
public class FlatHouseController {

  private final FlatHouseService flatHouseService;

  @PostMapping("/list")
  public ResponseEntity<FlatHouseListResponse> getList(
      @RequestBody FlatHouseConditionRequest flatHouseCondition) {
    return new ResponseEntity<FlatHouseListResponse>(
        flatHouseService.getList(flatHouseCondition.getPlace()), HttpStatus.OK);
  }

  @GetMapping("/detail")
  public ResponseEntity<FlatHouseDetailResponse> getDetail(
      @RequestParam String id) {
    return new ResponseEntity<FlatHouseDetailResponse>(flatHouseService.getDetail(id),
        HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<String> create(
      @RequestBody FlatHouseEditDto flatHouseEditDto) {
    try {
      flatHouseService.create(flatHouseEditDto);
      return new ResponseEntity<String>("やったね",
          HttpStatus.OK);
    } catch (LalinguaBussinessException e) {
      return new ResponseEntity<String>("だめだったよ",
          HttpStatus.BAD_REQUEST);
    }
  }


  @PostMapping("/approve")
  public ResponseEntity<String> approve(
      @RequestBody FlatHouseEditDto flatHouseEditDto) {
    try {
      flatHouseService.approve(flatHouseEditDto);
      return new ResponseEntity<String>("やったね",
          HttpStatus.OK);
    } catch (LalinguaBussinessException e) {
      return new ResponseEntity<String>("だめだったよ",
          HttpStatus.BAD_REQUEST);
    }
  }
}
