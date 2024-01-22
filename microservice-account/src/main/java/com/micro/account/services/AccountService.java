package com.micro.account.services;

import com.micro.account.dto.UpdateAccountDTO;
import com.micro.account.entities.Account;
import com.micro.account.entities.AccountPK;
import com.micro.account.http.response.CustomerByIdResponse;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    String findCustomerById(Long id);
    List<Account> findAllByIds(Long id);
    Optional<Account> findByPK(AccountPK pK);
    Account save(Account account);
    Optional<Account> update(AccountPK pK, UpdateAccountDTO client);
    Optional<Account> delete(AccountPK pK);
    Account findByIdNroCuentaAndIdTipoCuenta(Long nroCuenta, String tipoCuenta);
}
