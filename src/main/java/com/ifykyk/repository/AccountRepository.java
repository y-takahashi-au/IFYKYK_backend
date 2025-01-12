package com.ifykyk.repository;

import com.ifykyk.entity.Account;
import com.ifykyk.enums.AppliedStatus;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

  boolean existsAccountByUserId(String userId);

  boolean existsAccountByUserIdAndStatus(String userId, AppliedStatus status);

  Optional<Account> findByUserIdAndStatus(String userId, AppliedStatus status);

  Optional<Account> findByUserIdAndStatusIn(String userId, List<AppliedStatus> statuses);

  Optional<Account> findByUserIdAndUpdatedAtLessThanEqualAndStatus(String userId,
      Timestamp updatedAt, AppliedStatus status);

  long countByStatus(AppliedStatus status);

  List<Account> findByStatus(AppliedStatus status);
}
