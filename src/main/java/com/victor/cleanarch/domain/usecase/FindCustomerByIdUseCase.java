package com.victor.cleanarch.domain.usecase;

import com.victor.cleanarch.domain.entity.Customer;

import java.util.Optional;

public interface FindCustomerByIdUseCase {

    Customer findCustomerById(String id);

}
