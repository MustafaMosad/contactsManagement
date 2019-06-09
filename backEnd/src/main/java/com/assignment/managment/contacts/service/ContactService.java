package com.assignment.managment.contacts.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.managment.contacts.dao.ContactRepository;
import com.assignment.managment.contacts.dao.UserRepository;
import com.assignment.managment.contacts.dto.ContactForm;
import com.assignment.managment.contacts.dto.RetrieveContactsResponse;
import com.assignment.managment.contacts.dto.UserContactsForm;
import com.assignment.managment.contacts.exception.ContactNotFoundException;
import com.assignment.managment.contacts.exception.UserNotFoundException;
import com.assignment.managment.contacts.mapper.ContactMapper;
import com.assignment.managment.contacts.model.Contact;
import com.assignment.managment.contacts.model.User;

@Service
public class ContactService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired // inject user repository to access and perform queries on User entity
	private UserRepository userRepo;
	@Autowired // inject contact repository to access and perform queries on Contact entity
	private ContactRepository contactRepo;

	/**
	 * This method used to get user contacts based by paginate and sort the result
	 * and return only contacts result match the provide pagination and sorting
	 * parameters
	 * 
	 * @param userContactsForm
	 * @return
	 * @throws UserNotFoundException 
	 */
	public RetrieveContactsResponse getAllContactsByUserEmail(UserContactsForm userContactsForm) throws UserNotFoundException {
		logger.info("Start of getAllContactsByUserEmail");
		logger.debug(userContactsForm.toString());
		// get user by provided email
		User user = userRepo.findByEmail(userContactsForm.getEmail());
		if(user == null)
			throw new UserNotFoundException();
		logger.debug("found user with user email " + user.getEmail());
		// prepare filter object to fetch specific data from contact table
		Pageable pageFilter = PageRequest.of(userContactsForm.getPageNumber(), userContactsForm.getPageSize(),
				Sort.by(Sort.Direction.fromString(userContactsForm.getSortDirection()), userContactsForm.getSortBy()));

		List<Contact> contacts = contactRepo.findByUserId(user.getId(), pageFilter);
		// get total number of contacts belong to this user
		Long totalContacts = contactRepo.countByUserId(user.getId());
		// convert contacts dao objects to dto objects to return it back to web services
		List<ContactForm> contactsDto = ContactMapper.getDtoContactsList(contacts);

		logger.debug("contacts for user  " + userContactsForm.getEmail() + " is : " + contactsDto.toString());
		logger.info("End of getAllContactsByUserEmail");

		return new RetrieveContactsResponse(contactsDto, totalContacts);

	}

	/**
	 * This method used to create a new contact and bind it to provided user
	 * 
	 * @param contactForm
	 * @throws UserNotFoundException 
	 */
	public void addContact(ContactForm contactForm) throws UserNotFoundException {
		logger.info("Start of addContact");
		logger.debug(contactForm.toString());

		User user = userRepo.findByEmail(contactForm.getUserEmail());
		// throw an run time exception in case user is null
		if(user == null)
			throw new UserNotFoundException();
		logger.debug("found user with user email " + user.getEmail());
		contactForm.setContactId(generateContactId());
		Contact contact = ContactMapper.getDao(contactForm);

		contact.setUser(user);
		contactRepo.save(contact);
		logger.info("End of addContact");

	}

	/**
	 * This method for Update User data
	 * 
	 * @param contactForm
	 * @throws ContactNotFoundException 
	 */
	public void updateContact(ContactForm contactForm) throws ContactNotFoundException {
		logger.info("Start of addContact");
		logger.debug(contactForm.toString());
		Contact contact = contactRepo.findByContactId(contactForm.getContactId());
		// throw an run time exception in case contact is null
		if(contact == null)
			throw new ContactNotFoundException();
		Contact updatedContact = ContactMapper.getDao(contactForm);
		logger.debug("contact founded successfully");
		updatedContact.setId(contact.getId());
		updatedContact.setUser(contact.getUser());

		contactRepo.save(updatedContact);
		logger.info("End of addContact");

	}

	/**
	 * This method for remove User contact by providing the contact id ( which is
	 * unique through all contacts )
	 * 
	 * @param contactId
	 * @throws ContactNotFoundException 
	 */
	public void removeContact(String contactId) throws ContactNotFoundException {
		logger.info("Start of removeContact");
		logger.debug("Contact Id is : " + contactId);

		Contact contact = contactRepo.findByContactId(contactId);
		// throw an run time exception in case contact is null
		if(contact == null)
			throw new ContactNotFoundException();
		logger.debug("contact founded successfully");

		contactRepo.delete(contact);
		logger.info("End of removeContact");

	}

	/**
	 * This is Helper method for just generating java UUId
	 * 
	 * @return
	 */
	private String generateContactId() {

		return java.util.UUID.randomUUID().toString();
	}
}
