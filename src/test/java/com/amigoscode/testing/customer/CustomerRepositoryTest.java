package com.amigoscode.testing.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        //Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "1234567890";
        Customer customer = Customer.builder()
                .id(id)
                .name("Jay")
                .phoneNumber(phoneNumber)
                .build();
        //When
        underTest.save(customer);

        //Then
        Optional<Customer> customerOptional = underTest.selectCustomerByPhoneNumber(phoneNumber);
        assertThat(customerOptional).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
        });
    }

    @Test
    void itShouldSaveCustomer() {
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = Customer.builder()
                .id(id)
                .name("Jay")
                .phoneNumber("1234567890")
                .build();
        //when
        Customer savedCustomer = underTest.save(customer);

        //then
        Optional<Customer> customerOptional = underTest.findById(id);

        assertThat(customerOptional).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo("Jay");
            assertThat(c.getPhoneNumber()).isEqualTo("1234567890");
        });
    }

    @Test
    void itShouldSelectAllCustomers() {
        //Given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Jay")
                .phoneNumber("1234567890")
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Jay")
                .phoneNumber("1234567890")
                .build();
        //When
        underTest.save(customer);
        underTest.save(customer2);

        //Then
        Iterable<Customer> customers = underTest.findAll();

        assertThat(customers).isNotEmpty();
    }

    @Test
    void itShouldNotSelectCustomerWhenPhoneNumberDoesNotExists() {
        //Given
        String phoneNumber = "00000";

        //When
        Optional<Customer> customerOptional = underTest.selectCustomerByPhoneNumber(phoneNumber);

        //Then
        assertThat(customerOptional).isNotPresent();
    }
}