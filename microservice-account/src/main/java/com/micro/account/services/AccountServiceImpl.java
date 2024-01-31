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

    /**
     * Busca todas las cuentas de todos los clientes
     * @return Objeto de tipo List[Account]
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }

    /**
     * Busca un cliente por su id pero retorna solamente su nombre
     * @param id Identificador del cliente (no su identificación)
     * @return String con el nombre del cliente
     */
    @Override
    @Transactional(readOnly = true)
    public String findCustomerById(Long id) {
        CustomerDTO customerDTO = customerClient.findCustomerById(id);
        if(customerDTO == null) return "";
        return customerDTO.getNombre();
    }

    /**
     * Busca un cliente por su id y retorna todos sus atributos
     * @param id Identificador del cliente
     * @return Objeto de tipo CustomerDTO
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findAllCustomerById(Long id) {
        return customerClient.findCustomerById(id);
    }

    /**
     * Busca las cuentas asociadas a un cliente dado su id
     * @param id Identificador del cliente (no su identificación)
     * @return Objeto de tipo List[Account]
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> findAllByIds(Long id) {
        return accountRepository.findAllByIds(id);
    }

    /**
     * Busca una cuenta a través de su llave primaria (compuesta)
     * @param pK Objeto llave primaria de la cuenta
     * @return Obtejo de tipo Optional[Account]
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByPK(AccountPK pK) {
        return accountRepository.findById(pK);
    }

    /**
     * Guarda en la DB una cuenta de un cliente
     * @param account Objeto Account con los datos de la cuenta
     * @return Objeto de tipo Account
     */
    @Override
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Actualiza los datos de la cuenta de un cliente
     * @param pK Objeto AccountPK llave primaria de la cuenta
     * @param account Objeto AccountDTO con los campos a actualizar
     * @return Objeto de tipo Optional[Account]
     */
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

    /**
     * Elimina una cuenta de un cliente
     * @param pK Objeto AccountPK que contiene la llave de la cuenta
     * @return Objeto de tipo Optional[Account]
     */
    @Override
    @Transactional
    public Optional<Account> delete(AccountPK pK) {
        Optional<Account> accountOpt = accountRepository.findById(pK);
        accountOpt.ifPresent(a -> accountRepository.delete(a));
        return accountOpt;
    }

    /**
     * Busca una cuenta a través del número y tipo únicamente (no se considera el id)
     * @param nroCuenta Número de cuenta del cliente
     * @param tipoCuenta Tipo de cuenta (ahorros/corriente)
     * @return Objeto de tipo Account
     */
    @Override
    @Transactional(readOnly = true)
    public Account findByIdNroCuentaAndIdTipoCuenta(Long nroCuenta, String tipoCuenta) {
        return accountRepository.findByIdNroCuentaAndIdTipoCuenta(nroCuenta, tipoCuenta);
    }
}
