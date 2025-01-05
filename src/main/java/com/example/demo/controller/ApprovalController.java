package com.example.demo.controller;

import com.example.demo.dto.ApprovalAccountInfoList;
import com.example.demo.dto.ApprovalCountResponse;
import com.example.demo.dto.ApprovalFlatHouseReviewResponse;
import com.example.demo.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {

  private final ApprovalService approvalService;

  @GetMapping("/total_count")
  public ResponseEntity<ApprovalCountResponse> getCount() {
    return new ResponseEntity<ApprovalCountResponse>(
        approvalService.getCount(), HttpStatus.OK);
  }

  @GetMapping("/account_list")
  public ResponseEntity<ApprovalAccountInfoList> getAccountList() {
    return new ResponseEntity<ApprovalAccountInfoList>(
        approvalService.getAccountList(), HttpStatus.OK);
  }

  @GetMapping("/flat_house_review_list")
  public ResponseEntity<ApprovalFlatHouseReviewResponse> getFlatHouseReviewList() {
    return new ResponseEntity<ApprovalFlatHouseReviewResponse>(
        approvalService.getFlatHouseReviewList(), HttpStatus.OK);
  }
}
