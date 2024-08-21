package com.app.springboot_loginapp.controller;

import com.app.springboot_loginapp.dto.LoginResponse;
import com.app.springboot_loginapp.entity.User;
import com.app.springboot_loginapp.repository.UserRepository;
import com.app.springboot_loginapp.service.LoginService;
import com.app.springboot_loginapp.service.OtpService;
import com.app.springboot_loginapp.util.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/userLogin")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User user) {
    	User user1 = userRepository.findByUsername(user.getUsername());
        if (loginService.loginUser(user)) 
        {
            String message = loginService.isFirstTime(user) ? "NewPassword" : "home";
            System.out.println();
            String token = null;
            
            if ("home".equals(message)) {
                token = jwtUtil.generateToken(user.getUsername(),"Admin");
            }
            
            LoginResponse response = new LoginResponse(message, token);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Invalid username or password", null));
        }
    }

    @GetMapping("/protected-endpoint")
    public ResponseEntity<?> getProtectedData(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok("Protected data");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @PostMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody User user) {
        if (loginService.updatePassword(user)) {
            return ResponseEntity.status(HttpStatus.OK).body("login");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    @PostMapping("/sendOtp")
    public ResponseEntity<Object> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = otpService.generateOtp(); 
		otpService.sendOtp(email, otp);
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<Object> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        if(otpService.verifyOtp(email, otp)){
        return ResponseEntity.status(HttpStatus.OK).body("success");
        }
        else {
        	return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error");
        }
    }
}