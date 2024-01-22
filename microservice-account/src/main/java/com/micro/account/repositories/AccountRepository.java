package com.micro.account.repositories;

import com.micro.account.entities.Account;
import com.micro.account.entities.AccountPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, AccountPK> {
    @Query("SELECT a FROM Account a WHERE a.id.clientId = :clientId")
    List<Account> findAllByIds(@Param("clientId") Long id);
    Account findByIdNroCuentaAndIdTipoCuenta(Long nroCuenta, String tipoCuenta);
}
