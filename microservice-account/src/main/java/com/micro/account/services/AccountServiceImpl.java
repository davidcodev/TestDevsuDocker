package com.micro.account.services;

import com.micro.account.dto.CustomerDTO;
import com.micro.account.dto.UpdateAccountDTO;
import com.micro.account.entities.Account;
import com.micro.account.entities.AccountPK;
import com.micro.account.feing.CustomerClient;
import com.micro.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerClient customerClient;

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String findCustomerById(Long id) {
        CustomerDTO customerDTO = customerClient.findCustomerById(id);
        if(customerDTO == null) return "";
        return customerDTO.getNombre();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAllByIds(Long id) {
        return accountRepository.findAllByIds(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByPK(AccountPK pK) {
        return accountRepository.findById(pK);
    }

    @Override
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Optional<Account> update(AccountPK pK, UpdateAccountDTO account) {
        Optional<Account> accountOpt = accountRepository.findById(pK);
        if(accountOpt.isPresent()){
            Account accountDB = accountOpt.orElseThrow();
            accountDB.setId(pK);
            accountDB.setSaldoInicial(account.getSaldoInicial());
            accountDB.setEstado(account.getEstado());
            return Optional.of(accountRepository.save(accountDB));
        }
        return accountOpt;
    }

    @Override
    @Transactional
    public Optional<Account> delete(AccountPK pK) {
        Optional<Account> accountOpt = accountRepository.findById(pK);
        accountOpt.ifPresent(a -> accountRepository.delete(a));
        return accountOpt;
    }

    @Override
    @Transactional(readOnly = true)
    public Account findByIdNroCuentaAndIdTipoCuenta(Long nroCuenta, String tipoCuenta) {
        return accountRepository.findByIdNroCuentaAndIdTipoCuenta(nroCuenta, tipoCuenta);
    }
}
