package com.abhinavjdwij.poc.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhinavjdwij.poc.model.EmailRequestResponse;
import com.abhinavjdwij.poc.service.MailSenderService;

@RestController("/mail")
public class MailSenderController {
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@PostMapping(value = "/send", consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<EmailRequestResponse> sendMail(@RequestBody EmailRequestResponse request) {
		EmailRequestResponse response = new EmailRequestResponse();
		BeanUtils.copyProperties(request, response);
		try {
			mailSenderService.sendEmail(request);
			response.setStatus("Success");
			response.setMessage("Mail sent successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus("Failure");
			response.setMessage(ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
