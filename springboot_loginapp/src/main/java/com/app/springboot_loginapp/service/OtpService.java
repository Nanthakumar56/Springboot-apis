package com.app.springboot_loginapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.springboot_loginapp.entity.User;
import com.app.springboot_loginapp.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OtpService {

	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private JavaMailSender mailSender;
    
    @PostConstruct
    public void init() {
        if (mailSender == null) {
            System.out.println("JavaMailSender is null");
            
        } else {
            System.out.println("JavaMailSender is initialized");
        }
    }
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is " + otp);
        mailSender.send(message);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plusMinutes(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedFutureTime = futureTime.format(formatter);
        LocalDateTime parsedFutureTime = LocalDateTime.parse(formattedFutureTime, formatter);
        User existingUser = userRepository.findByEmail(to);
        if (existingUser != null) {
            existingUser.setOtp(otp);
            existingUser.setOtp_expiration(parsedFutureTime);
            userRepository.save(existingUser); 
        } 
    }
    public boolean verifyOtp(String email, String otp) 
    {
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        LocalDateTime parsedTime = LocalDateTime.parse(formattedNow, formatter);
        
        String db_otp = "";
        LocalDateTime db_expiration = null;
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            db_otp = existingUser.getOtp();
            db_expiration = existingUser.getOtp_expiration();
        }
        if(db_otp.equals(otp) && parsedTime.isBefore(db_expiration))
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
}