package com.micro.customer.controllers;

import com.micro.customer.dto.CustomerDTO;
import com.micro.customer.dto.CustomerUpdateDTO;
import com.micro.customer.entities.Client;
import com.micro.customer.models.ErrorModel;
import com.micro.customer.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    /**
     * Inyección de la interfaz ClienteService
     */
    @Autowired
    private ClientService clientService;

    /**
     * Endpoint para listar TODOS los campos de los Clientes
     * @return listado de clientes
     */
    @GetMapping("/listartodo")
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<Client> findAllData() {
        return clientService.findAll();
    }

    /**
     * Endpoint para listar SOLO los campos que muestra el caso de uso del ejemplo
     * @return listado de clientes con ciertos campos
     */
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public ResponseEntity<List<CustomerDTO>> findCustomers() {
        List<CustomerDTO> clients = clientService
                .findAll()
                .stream()
                .map(c -> new CustomerDTO(
                        c.getNombre(),
                        c.getDireccion(),
                        c.getTelefono(),
                        c.getContrasena(),
                        c.getEstado())).toList();
        return ResponseEntity.ok(clients);
    }

    /**
     * Endpoint para buscar un cliente por su Identificador o código
     * @param id identificador único (clientId)
     * @return cliente / error
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> findById(@Valid @PathVariable Long id) {
        Optional<Client> clientOpt = clientService.findById(id);
        if(clientOpt.isPresent())
            return ResponseEntity.ok(clientOpt);

        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "El registro con id: " + id +" no existe.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Endpoint para crear un nuevo cliente
     * @param client campos del cliente
     * @return cliente / error
     */
    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
        if(result.hasFieldErrors())
            return validation(result);
        Optional<Client> clientOpt = clientService.findByIdentificacion(client.getIdentificacion());
        if(clientOpt.isPresent()){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Ya existe un cliente con la identificación: " + client.getIdentificacion());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
    }

    /**
     * Endpont para actualizar un cliente existente
     * @param id Identificador único del cliente (clientId)
     * @param client Datos del cliente
     * @return cliente actualizado / error
     */
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CustomerUpdateDTO client, BindingResult result, @PathVariable Long id) {
        if(result.hasFieldErrors())
            return validation(result);
        Optional<Client> clientOpt = clientService.update(id, client);
        if(clientOpt.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(clientOpt);
        ErrorModel error = new ErrorModel(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                "No es posible actualizar el cliente, su id no existe");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Endpoint para eliminar un cliente existente
     * @param id Identificador único de cliente (clientId)
     * @return cliente eliminado / error
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Client> clientOpt = clientService.delete(id);
        if(clientOpt.isPresent()) return ResponseEntity.ok(clientOpt);
        ErrorModel error = new ErrorModel(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                "No es posible eliminar el cliente, su id no existe");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Endpoint que retorna el nombre de cliente dado su Id (para el micro de cuentas)
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<?> findByIdId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    /**
     * El siguiente método valida los campos de una trama
     * @param result Objeto validado
     * @return Mensaje de error con el estatus Bad_Request
     */
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
