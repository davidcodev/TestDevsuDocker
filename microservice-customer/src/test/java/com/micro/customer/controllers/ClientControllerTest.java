package com.micro.customer.controllers;

import com.micro.customer.entities.Client;
import com.micro.customer.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientControllerTest {
    @Autowired
    private ClientService clientService;

    @Test
    void findAllData() {
        List<Client> clients = clientService.findAll();
        assertNotNull(clients);
    }
}
