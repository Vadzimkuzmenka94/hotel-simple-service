
package com.example.hotelsimpleservice.repository;

import com.example.hotelsimpleservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    String FIND_BY_LOGIN = "SELECT customer_id, " +
            "name, " +
            "surname, " +
            "login, " +
            "password, " +
            "role, " +
            "email, " +
            "card_number " +
            "FROM customer WHERE customer.login = :login";

    @Query(value = FIND_BY_LOGIN, nativeQuery = true)
    Optional<Customer> findByLogin(String login);
}
