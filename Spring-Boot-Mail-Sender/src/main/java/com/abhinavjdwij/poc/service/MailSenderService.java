package com.abhinavjdwij.poc.service;

import java.io.File;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.abhinavjdwij.poc.model.EmailRequestResponse;

@Service
public class MailSenderService {
	
	@Value("${spring.mail.username}")
	private String fromEmailAddress;
	
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(EmailRequestResponse request) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setSubject(request.getSubject());
		
		helper.setText(request.getBody());
		
		if ( ! CollectionUtils.isEmpty(request.getToList()) ) {
			InternetAddress[] toAddressList = new InternetAddress[request.getToList().size()];
			for (int i = 0; i < toAddressList.length; i++) {
				toAddressList[i] = new InternetAddress(request.getToList().get(i));
			}
			helper.setTo(toAddressList);
		} else {
			throw new Exception("To Address List cannot be empty");
		}
		
		if (  ! CollectionUtils.isEmpty(request.getCcList()) ) {
			InternetAddress[] ccAddressList = new InternetAddress[request.getCcList().size()];
			for (int i = 0; i < ccAddressList.length; i++) {
				ccAddressList[i] = new InternetAddress(request.getCcList().get(i));
			}
			helper.setCc(ccAddressList);
		}
		
		if ( ! CollectionUtils.isEmpty(request.getBccList()) ) {
			InternetAddress[] bccAddressList = new InternetAddress[request.getBccList().size()];
			for (int i = 0; i < bccAddressList.length; i++) {
				bccAddressList[i] = new InternetAddress(request.getBccList().get(i));
			}
			helper.setBcc(bccAddressList);
		}
		
		helper.setSubject(request.getSubject());
		
		helper.setText(request.getBody());
		
		if ( ! CollectionUtils.isEmpty(request.getAttachmentPathList()) ) {
			for (String attachmentPath : request.getAttachmentPathList()) {
				FileSystemResource attachment  = new FileSystemResource(new File(attachmentPath));
			    helper.addAttachment(attachmentPath.toString(), attachment);
			}
		}
		
		mailSender.send(message);
		
	}

}
