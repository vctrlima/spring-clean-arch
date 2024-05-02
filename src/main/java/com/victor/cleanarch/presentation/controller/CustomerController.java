package com.victor.cleanarch.presentation.controller;

import com.victor.cleanarch.domain.entity.Customer;
import com.victor.cleanarch.domain.usecase.FindCustomerByIdUseCase;
import com.victor.cleanarch.domain.usecase.InsertCustomerUseCase;
import com.victor.cleanarch.presentation.model.dto.FindCustomerByIdResponseDTO;
import com.victor.cleanarch.presentation.model.dto.InsertCustomerRequestDTO;
import com.victor.cleanarch.presentation.model.dto.InsertCustomerResponseDTO;
import com.victor.cleanarch.presentation.model.mapper.CustomerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private InsertCustomerUseCase insertCustomerUseCase;

    @Autowired
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<InsertCustomerResponseDTO> insert(@Valid @RequestBody InsertCustomerRequestDTO dto) {
        Customer customer = customerMapper.fromInsertRequestDTOtoEntity(dto);
        Customer entity = insertCustomerUseCase.insert(customer, dto.getZipCode());
        InsertCustomerResponseDTO response = customerMapper.fromEntityToInsertResponseDTO(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindCustomerByIdResponseDTO> findById(@NotBlank @PathVariable String id) {
        Optional<Customer> entity = findCustomerByIdUseCase.findCustomerById(id);
        if (entity.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        FindCustomerByIdResponseDTO response = customerMapper.fromEntityToFindByIdResponseDTO(entity.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
