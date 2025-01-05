package com.example.demo.service;

import com.example.demo.dto.FlatHouseDetailResponse;
import com.example.demo.dto.FlatHouseEditDto;
import com.example.demo.dto.FlatHouseListResponse;
import com.example.demo.dto.FlatHouseOverviewResponse;
import com.example.demo.dto.FlatHouseReviewResponse;
import com.example.demo.entity.FlatHouse;
import com.example.demo.entity.FlatHousePicture;
import com.example.demo.entity.FlatHouseReview;
import com.example.demo.enums.AppliedStatus;
import com.example.demo.enums.PicturePath;
import com.example.demo.exception.LalinguaBussinessException;
import com.example.demo.repository.AccountInfoRepository;
import com.example.demo.repository.FlatHousePictureRepository;
import com.example.demo.repository.FlatHouseRepository;
import com.example.demo.repository.FlatHouseRepository.Overview;
import com.example.demo.repository.FlatHouseReviewRepository;
import com.example.demo.service.helper.AccountHelper;
import com.example.demo.service.helper.CloudStorageHelper;
import com.example.demo.utils.CreateULID;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlatHouseService {

  private final FlatHouseRepository flatHouseRepository;

  private final FlatHouseReviewRepository flatHouseReviewRepository;

  private final FlatHousePictureRepository flatHousePictureRepository;

  private final AccountInfoRepository accountInfoRepository;

  private final AccountHelper accountHelper;

  private final CloudStorageHelper cloudStorageHelper;

  @Transactional(readOnly = true)
  public FlatHouseListResponse getList(String place) {
    var overViews = StringUtils.isEmpty(place) ? this.getOverviews(place) : this.getOverviews();
    var flatHouseOverviews = overViews.stream().map(flatHouse -> {
      var flatHouseOverview = new FlatHouseOverviewResponse();
      flatHouseOverview.setId(flatHouse.getId());
      flatHouseOverview.setAddress(flatHouse.getAddress());
      flatHouseOverview.setStar(flatHouse.getStar());
      if (StringUtils.isNotEmpty(flatHouse.getPicturePath())) {
        flatHouseOverview.setPicture(
            cloudStorageHelper.downloadObjectIntoMemory(flatHouse.getPicturePath()));
      }
      return flatHouseOverview;
    }).collect(Collectors.toList());

    var flatHouseList = new FlatHouseListResponse();
    flatHouseList.setFlatHouseOverviews(flatHouseOverviews);
    return flatHouseList;
  }

  private List<Overview> getOverviews(String place) {
    return flatHouseRepository.findAllOverviewsByPlace(place);
  }

  private List<Overview> getOverviews() {
    return flatHouseRepository.findAllOverviews();
  }

  @Transactional(readOnly = true)
  public FlatHouseDetailResponse getDetail(String id) {
    return flatHouseRepository.findById(id).map(this::setFlatHouseDetail)
        .orElseThrow(() -> new LalinguaBussinessException("flatHouseId not found."));
  }

  private FlatHouseDetailResponse setFlatHouseDetail(FlatHouse flatHouse) {
    var detail = new FlatHouseDetailResponse();
    detail.setId(flatHouse.getId());
    detail.setAddress(flatHouse.getAddress());
    detail.setFlatHouseReviews(this.setFlatHouseReviews(flatHouse.getFlatHouseReviews()));
    return detail;
  }


  private List<FlatHouseReviewResponse> setFlatHouseReviews(
      List<FlatHouseReview> flatHouseReviews) {
    return flatHouseReviews.stream().map(flatHouseReview -> {
      var flatHouseReviewResponse = new FlatHouseReviewResponse();
      flatHouseReviewResponse.setId(flatHouseReview.getId());
      flatHouseReviewResponse.setRent(flatHouseReview.getRent());
      flatHouseReviewResponse.setMoveIn(flatHouseReview.getMoveIn());
      flatHouseReviewResponse.setMoveOut(flatHouseReview.getMoveOut());
      flatHouseReviewResponse.setStar(flatHouseReview.getStar());
      flatHouseReviewResponse.setReview(flatHouseReview.getReview());
      flatHouseReviewResponse.setAccountId(flatHouseReview.getAccountId());
      flatHouseReviewResponse.setAccountName(this.getAccountName(flatHouseReview.getAccountId()));
      flatHouseReviewResponse.setStar(flatHouseReview.getStar());
      flatHouseReviewResponse.setPictures(
          flatHouseReview.getFlatHousePictures().stream().map(
              flatHousePicture -> cloudStorageHelper.downloadObjectIntoMemory(
                  flatHousePicture.getPicturePath())).collect(Collectors.toList()));
      return flatHouseReviewResponse;
    }).collect(Collectors.toList());
  }

  private String getAccountName(String accountId) {
    return accountInfoRepository.findById(accountId)
        .map(accountInfo -> accountInfo.getGivenName() + " " + accountInfo.getGivenName())
        .orElseThrow(() -> new LalinguaBussinessException("flatHouseId not found."));
  }

  @Transactional()
  public void create(FlatHouseEditDto flatHouseEditDto) {
    if (accountHelper.existUserIdAndApproved(flatHouseEditDto.getAccountId())) {
      throw new LalinguaBussinessException("account not found.");
    }
    var flatHouse = this.createFlatHouse(flatHouseEditDto.getAddress());
    var flatHouseReview = this.createFlatHouseReview(flatHouse.getId(), flatHouseEditDto);
    var flatHousePictures = this.createFlatHousePicture(flatHouseReview.getId(),
        flatHouseEditDto.getPictures());
    flatHouseReview.setFlatHousePictures(flatHousePictures);
    flatHouse.setFlatHouseReviews(List.of(flatHouseReview));
    flatHouseRepository.save(flatHouse);
  }

  @Transactional()
  public void approve(FlatHouseEditDto flatHouseEditDto) {

    flatHousePictureRepository.deleteByFlatHouseReviewId(flatHouseEditDto.getId());
    flatHousePictureRepository.flush();
    var flatHouseReview = flatHouseReviewRepository.findById(flatHouseEditDto.getId())
        .orElseThrow(() -> new LalinguaBussinessException("flatHouseId not found."));

    flatHouseReview.setRent(flatHouseEditDto.getRent());
    flatHouseReview.setMoveIn(
        flatHouseEditDto.getMoveIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    flatHouseReview.setMoveOut(
        flatHouseEditDto.getMoveOut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    flatHouseReview.setStar(flatHouseEditDto.getStar());
    flatHouseReview.setReview(flatHouseEditDto.getReview());
    flatHouseReview.setStatus(AppliedStatus.APPROVED);
    deleteFlatHousePicture(flatHouseReview.getFlatHousePictures());
    //なぜ以下の処理で削除できないのか
//    for (
//        Iterator<FlatHousePicture> flatHousePictureIterator = flatHouseReview.getFlatHousePictures()
//            .iterator();
//        flatHousePictureIterator.hasNext(); ) {
//      flatHousePictureIterator.next();
//      flatHousePictureIterator.remove();
//    }
    flatHouseReviewRepository.saveAndFlush(flatHouseReview);
    flatHouseReview.getFlatHousePictures()
        .addAll(this.createFlatHousePicture(flatHouseReview.getId(),
            flatHouseEditDto.getPictures()));
    flatHouseReviewRepository.save(flatHouseReview);
  }

  private FlatHouse createFlatHouse(String address) {
    var flatHouse = new FlatHouse();
    flatHouse.setAddress(address);
    return flatHouse;
  }

  private FlatHouseReview createFlatHouseReview(String flatHouseId,
      FlatHouseEditDto flatHouseEditDto) {
    var flatHouseReview = new FlatHouseReview();
    flatHouseReview.setFlatHouseId(flatHouseId);
    flatHouseReview.setRent(flatHouseEditDto.getRent());
    flatHouseReview.setMoveIn(
        flatHouseEditDto.getMoveIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    flatHouseReview.setMoveOut(
        flatHouseEditDto.getMoveOut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    flatHouseReview.setStar(flatHouseEditDto.getStar());
    flatHouseReview.setReview(flatHouseEditDto.getReview());
    flatHouseReview.setAccountId(flatHouseEditDto.getAccountId());
    flatHouseReview.setStatus(AppliedStatus.APPLIED);
    return flatHouseReview;
  }

  private List<FlatHousePicture> createFlatHousePicture(String flatHouseReviewId,
      List<String> base64s) {
    return base64s.stream().map(base64 -> {
      var ulid = CreateULID.UUID();
      var path = PicturePath.RESTAURANT.getPath() + ulid + ".jpg";
      var flatHousePicture = new FlatHousePicture();
      flatHousePicture.setId(ulid);
      flatHousePicture.setFlatHouseReviewId(flatHouseReviewId);
      flatHousePicture.setPicturePath(path);
      cloudStorageHelper.uploadObject(base64, path);
      return flatHousePicture;
    }).collect(Collectors.toList());
  }

  private void deleteFlatHousePicture(List<FlatHousePicture> FlatHousePictures) {
    FlatHousePictures.forEach(FlatHousePicture -> {
      cloudStorageHelper.deleteObject(FlatHousePicture.getPicturePath());
    });

  }
}
