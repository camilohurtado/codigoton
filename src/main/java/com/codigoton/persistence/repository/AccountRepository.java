package com.codigoton.persistence.repository;

import com.codigoton.persistence.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Long, Account> {

    List<Account> findAllByClientId(long clientId);
}
