package com.micro.account.feing;

import com.micro.account.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Objeto que establece los métodos para comunicarse con el microservicio de clientes
 */
@FeignClient(name = "microservice-customer", url = "microservice-customer:8090/clientes")
public interface CustomerClient {
    @GetMapping("/search-by-id/{id}")
    CustomerDTO findCustomerById(@PathVariable Long id);
}
