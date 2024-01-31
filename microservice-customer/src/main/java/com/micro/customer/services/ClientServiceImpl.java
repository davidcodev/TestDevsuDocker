package com.micro.customer.services;

import com.micro.customer.dto.CustomerUpdateDTO;
import com.micro.customer.entities.Client;
import com.micro.customer.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder bCrypt;

    public ClientServiceImpl(BCryptPasswordEncoder bCrypt) {
        this.bCrypt = bCrypt;
    }

    /**
     * Busca todos los clientes registrados
     * @return Lista de tipo [Client]
     */
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    /**
     * Busca un cliente por su Id (no identificación)
     * @param id Identificador del cliente
     * @return Objeto de tipo Optional[Client]
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    /**
     * Busca un cliente por su Identificación (no id)
     * @param id Número de identificación del cliente
     * @return Objeto de tipo Optional[Client]
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByIdentificacion(String id) {
        return clientRepository.findByIdentificacion(id);
    }

    /**
     * Guarda en la DB el cliente
     * @param client Objeto Client
     * @return Objeto Client almacenado
     */
    @Override
    @Transactional
    public Client save(Client client) {
        client.setContrasena(bCrypt.encode(client.getContrasena()));
        return clientRepository.save(client);
    }

    /**
     * Actualiza los datos de un cliente a través de su id
     * @param id Identificador del cliente
     * @param client Objeto Client a actualizar
     * @return Objeto de tipo Optional[Client]
     */
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
            clientDB.setContrasena(bCrypt.encode(client.getContrasena()));
            clientDB.setEstado(client.getEstado());
            return Optional.of(clientRepository.save(clientDB));
        }
        return clientOpt;
    }

    /**
     * Elimina a un cliente de la DB
     * @param id Identificador del cliente
     * @return Objeto de tipo Optional[Client]
     */
    @Override
    @Transactional
    public Optional<Client> delete(Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(c -> clientRepository.delete(c));
        return clientOpt;
    }
}
