package com.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entity.UserEntity;
import com.user.service.serviceInf.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {

		return new ResponseEntity<>(this.userService.addUser(userEntity), HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {

		List<UserEntity> users = this.userService.getAllUsers();

		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable String id) {

		return new ResponseEntity<>(this.userService.getUser(id), HttpStatus.OK);

	}

}
