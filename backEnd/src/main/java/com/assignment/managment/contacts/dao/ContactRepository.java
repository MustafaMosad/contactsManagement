package com.assignment.managment.contacts.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.managment.contacts.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	public List<Contact> findByUserId(Long id, Pageable pageable);

	public Contact findByContactId(String contactId);
	public Long countByUserId(Long id);
}
