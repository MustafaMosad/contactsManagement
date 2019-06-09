package com.assignment.managment.contacts.security.jwt.resources;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.managment.contacts.dto.RegistrationForm;
import com.assignment.managment.contacts.exception.ExceptionResponse;
import com.assignment.managment.contacts.security.jwt.JwtTokenUtil;
import com.assignment.managment.contacts.security.jwt.JwtUserDetails;
import com.assignment.managment.contacts.security.jwt.JwtUserDetailsService;
import com.assignment.managment.contacts.service.RegistrationConfirmationService;
import com.assignment.managment.contacts.service.RegistrationService;

@RestController
public class JwtAuthenticationRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private RegistrationConfirmationService registrationConfirmationService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody @Valid RegistrationForm registrationForm) {

		ExceptionResponse exceptionResponse = registrationService.isEmailExist(registrationForm.getEmail());
		if (exceptionResponse != null) {
			return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
		}
		registrationService.saveUser(registrationForm.getEmail(), registrationForm.getPassword());
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ResponseEntity<?> confirmRegistration(@RequestBody AccountConfirmationToken accountConfirmationToken) {
		try {
			logger.info(accountConfirmationToken.getToken());
			registrationConfirmationService.activateUseraccount(accountConfirmationToken.getToken());
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
			throws AuthenticationException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		if (!((JwtUserDetailsService) jwtUserDetailsService).isUserVerfied(authenticationRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}

	@RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUserDetails user = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
