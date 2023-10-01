package com.amigoscode.testing.customer;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistrationServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRegistrationService underTest;


    @BeforeEach
    void setUp() {

    }

}