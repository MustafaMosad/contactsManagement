package com.assignment.managment.contacts.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.managment.contacts.dto.ContactForm;
import com.assignment.managment.contacts.dto.RetrieveContactsResponse;
import com.assignment.managment.contacts.dto.UserContactsForm;
import com.assignment.managment.contacts.exception.ContactNotFoundException;
import com.assignment.managment.contacts.exception.UserNotFoundException;
import com.assignment.managment.contacts.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ContactService contactService;

	@RequestMapping(value = "/retrieveUserContacts", method = RequestMethod.GET)
	public ResponseEntity<?> getUserContacts(@RequestParam String email, @RequestParam Integer pageNumber,
			@RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortDirection) {
		logger.info("Start of getUserContacts");

		try {
			UserContactsForm userContactsForm = new UserContactsForm(email, pageNumber, pageSize, sortBy,
					sortDirection);
			userContactsForm.setEmail(email);
			RetrieveContactsResponse retrieveContactsResponse = contactService
					.getAllContactsByUserEmail(userContactsForm);
			logger.info("end of getUserContacts");
			return ResponseEntity.ok(retrieveContactsResponse);
		} catch (UserNotFoundException e) {
			logger.warn("error {} ", e);
			logger.info("end of getUserContacts");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("error {} ", e);
			logger.info("end of getUserContacts");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(value = "/addUserContact", method = RequestMethod.POST)
	public ResponseEntity<?> addUserContact(@RequestBody @Valid ContactForm contactForm) {
		logger.info("Start of addUserContact");

		try {
			contactService.addContact(contactForm);
			logger.info("End of addUserContact");
			return ResponseEntity.ok(contactForm);
		} catch (UserNotFoundException e) {
			logger.warn("error {} ", e);
			logger.info("end of getUserContacts");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("error {} ", e);
			logger.info("End of addUserContact");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(value = "/updateUserContact", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserContact(@RequestBody @Valid ContactForm contactForm) {
		logger.info("Start of updateUserContact");

		try {
			contactService.updateContact(contactForm);
			return ResponseEntity.ok(contactForm);
		} catch (ContactNotFoundException e) {
			logger.warn("error {} ", e);
			logger.info("end of updateUserContact");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("error {} ", e);
			logger.info("end of updateUserContact");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(value = "/removeUserContact/{contactId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeUserContact(@PathVariable String contactId) {
		logger.info("Start of removeUserContact");

		try {
			contactService.removeContact(contactId);
			return ResponseEntity.ok().build();
		}catch (ContactNotFoundException e) {
			logger.warn("error {} ", e);
			logger.info("end of removeUserContact");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("error {} ", e);
			logger.info("end of removeUserContact");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
