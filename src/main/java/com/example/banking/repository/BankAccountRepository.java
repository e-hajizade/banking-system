package com.example.banking.repository;

import com.example.banking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select account from BankAccount account where account.accountId=:accountId")
	Optional<BankAccount> findAccountForUpdate(@Param("accountId") Long accountId);
}
