package com.micro.customer.repositories;

import com.micro.customer.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    /**
     * Se configura un método para buscar a una persona por su identificación
     * @param id Identificación
     * @return Optional
     */
    Optional<Client> findByIdentificacion(String id);
}
