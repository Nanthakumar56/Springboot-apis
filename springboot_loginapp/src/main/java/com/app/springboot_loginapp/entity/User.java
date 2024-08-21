package com.app.springboot_loginapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
	private String password;
	private String email;
    private boolean is_first_login;
    private String role;
	private String otp;
    private LocalDateTime otp_expiration;

    public User() {
    }

	public User(String username, String password, String email, boolean is_first_login, String otp,String role, LocalDateTime otp_expiration) {
		super();
		this.username = username;
		this.password = password;
		this.setEmail(email);
		this.is_first_login = is_first_login;
		this.otp = otp;
		this.role = role;
		this.otp_expiration = otp_expiration;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
    public boolean getis_first_login() {
		return is_first_login;
	}

	public void setis_first_login(boolean is_first_login) {
		this.is_first_login = is_first_login;
	}
    public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtp_expiration() {
		return otp_expiration;
	}

	public void setOtp_expiration(LocalDateTime futureTime) {
		this.otp_expiration = futureTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}