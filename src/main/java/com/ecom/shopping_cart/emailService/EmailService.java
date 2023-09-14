package com.ecom.shopping_cart.emailService;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
    private String from;
	public boolean sendEmail(String subject, String message, String to) {
		
        try {
        	MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        	MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        	
        	mimeMessageHelper.setFrom(from);
        	mimeMessageHelper.setTo(to);
        	mimeMessageHelper.setSubject(subject);
        	mimeMessageHelper.setText(message);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

	}
}
