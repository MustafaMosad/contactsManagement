package com.assignment.managment.contacts.dao;

import org.springframework.data.repository.CrudRepository;

import com.assignment.managment.contacts.dto.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {  
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}