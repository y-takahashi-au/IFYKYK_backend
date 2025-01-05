package com.example.demo.service;

import com.example.demo.dto.AccountInfoDto;
import com.example.demo.dto.ApprovalAccountInfoList;
import com.example.demo.dto.ApprovalCountResponse;
import com.example.demo.dto.ApprovalFlatHouseReviewResponse;
import com.example.demo.dto.FlatHouseReviewDto;
import com.example.demo.enums.AppliedStatus;
import com.example.demo.exception.LalinguaBussinessException;
import com.example.demo.repository.AccountInfoRepository;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.FlatHouseRepository;
import com.example.demo.repository.FlatHouseReviewRepository;
import com.example.demo.service.helper.CloudStorageHelper;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalService {

  private final AccountRepository accountRepository;
  private final AccountInfoRepository accountInfoRepository;

  private final FlatHouseRepository flatHouseRepository;

  private final FlatHouseReviewRepository flatHouseReviewRepository;
  private final CloudStorageHelper cloudStorageHelper;

  @Transactional(readOnly = true)
  public ApprovalCountResponse getCount() {
    var accountCount = accountRepository.countByStatus(AppliedStatus.APPLIED);
    var flatHouseReviewCount = flatHouseReviewRepository.countByStatus(AppliedStatus.APPLIED);
    return new ApprovalCountResponse(accountCount, flatHouseReviewCount);
  }

  @Transactional(readOnly = true)
  public ApprovalAccountInfoList getAccountList() {
    var accounts = accountRepository.findByStatus(AppliedStatus.APPLIED);
    var accountInfos = accounts.stream().map(account -> {
      var accountInfoDto = new AccountInfoDto();
      accountInfoDto.setUserId(account.getUserId());
      var accountInfo = accountInfoRepository.findById(account.getId())
          .orElseThrow(() -> new LalinguaBussinessException("userId not found."));

      accountInfoDto.setGivenName(accountInfo.getGivenName());
      accountInfoDto.setFamilyName(accountInfo.getFamilyName());
      return accountInfoDto;
    }).collect(Collectors.toList());
    return new ApprovalAccountInfoList(accountInfos);
  }

  @Transactional(readOnly = true)
  public ApprovalFlatHouseReviewResponse getFlatHouseReviewList() {
    var flatHouseReviews = flatHouseReviewRepository.findByStatus(AppliedStatus.APPLIED);

    var flatHouseReviewDtoList = flatHouseReviews.stream().map(flatHouseReview -> {
      var flatHouseReviewDto = new FlatHouseReviewDto();
      flatHouseReviewDto.setReviewId(flatHouseReview.getId());
      flatHouseReviewDto.setRent(flatHouseReview.getRent());
      flatHouseReviewDto.setMoveIn(flatHouseReview.getMoveIn());
      flatHouseReviewDto.setMoveOut(flatHouseReview.getMoveOut());
      flatHouseReviewDto.setStar(flatHouseReview.getStar());
      flatHouseReviewDto.setReview(flatHouseReview.getReview());
      var flatHouse = flatHouseRepository.findById(flatHouseReview.getFlatHouseId())
          .orElseThrow(() -> new LalinguaBussinessException("flatHouseId not found."));
      flatHouseReviewDto.setAddress(flatHouse.getAddress());
      flatHouseReviewDto.setPictures(
          flatHouseReview.getFlatHousePictures().stream().map(
              flatHousePicture -> cloudStorageHelper.downloadObjectIntoMemory(
                  flatHousePicture.getPicturePath())).collect(Collectors.toList()));
      return flatHouseReviewDto;
    }).collect(Collectors.toList());
    return new ApprovalFlatHouseReviewResponse(flatHouseReviewDtoList);
  }
}
