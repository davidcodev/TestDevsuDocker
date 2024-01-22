package com.micro.customer.services;

import com.micro.customer.dto.CustomerUpdateDTO;
import com.micro.customer.entities.Client;
import com.micro.customer.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de los métodos para manejar Clientes
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByIdentificacion(String id) {
        return clientRepository.findByIdentificacion(id);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Optional<Client> update(Long id, CustomerUpdateDTO client) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if(clientOpt.isPresent()){
            Client clientDB = clientOpt.orElseThrow();
            clientDB.setNombre(client.getNombre());
            clientDB.setGenero(client.getGenero());
            clientDB.setEdad(client.getEdad());
            clientDB.setDireccion(client.getDireccion());
            clientDB.setTelefono(client.getTelefono());
            clientDB.setContrasena(client.getContrasena());
            clientDB.setEstado(client.getEstado());
            return Optional.of(clientRepository.save(clientDB));
        }
        return clientOpt;
    }

    @Override
    @Transactional
    public Optional<Client> delete(Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(c -> clientRepository.delete(c));
        return clientOpt;
    }
}
