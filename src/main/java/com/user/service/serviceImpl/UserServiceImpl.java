package com.user.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.Exception.ResourceNotFoundException;
import com.user.service.entity.UserEntity;
import com.user.service.repository.UserRepository;
import com.user.service.serviceInf.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserEntity addUser(UserEntity userEntity) {

		String uuid = UUID.randomUUID().toString();
		userEntity.setId(uuid);
		return this.repository.save(userEntity);

	}

	@Override
	public UserEntity getUser(String id) {

		UserEntity user = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found! Server error"));
//calling the rating service api from user service
		ArrayList forObject = restTemplate.getForObject("http://localhost:9004/api/rating/user/" + id, ArrayList.class);
		log.info("{}", forObject);
		user.setRatings(forObject);
		return user;
	}

	@Override
	public List<UserEntity> getAllUsers() {

		List<UserEntity> users = this.repository.findAll();

		for (int i = 0; i < users.size(); i++) {

			ArrayList forObject = restTemplate
					.getForObject("http://localhost:9004/api/rating/user/" + users.get(i).getId(), ArrayList.class);

			users.get(i).setRatings(forObject);
		}

		return users;
	}

}
