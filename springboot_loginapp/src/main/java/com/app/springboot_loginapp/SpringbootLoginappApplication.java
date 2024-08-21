package com.app.springboot_loginapp;

import java.security.Key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.springboot_loginapp.config.DataSourceConfig;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
public class SpringbootLoginappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoginappApplication.class, args);
		System.out.println("My application running ");
	}

}