package com.micro.customer.services;

import com.micro.customer.dto.CustomerUpdateDTO;
import com.micro.customer.entities.Client;
import java.util.List;
import java.util.Optional;

/**
 * Definición de los métodos para manejar Clientes
 */
public interface ClientService {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Optional<Client> findByIdentificacion(String id);
    Client save(Client client);
    Optional<Client> update(Long id, CustomerUpdateDTO client);
    Optional<Client> delete(Long id);
}
