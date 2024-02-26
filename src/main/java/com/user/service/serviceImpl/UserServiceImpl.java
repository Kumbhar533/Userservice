package com.user.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.Client.FeignClientInf;
import com.user.service.Exception.ResourceNotFoundException;
import com.user.service.entity.Hotel;
import com.user.service.entity.Rating;
import com.user.service.entity.UserEntity;
import com.user.service.repository.UserRepository;
import com.user.service.serviceInf.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private FeignClientInf clientInf;
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
		Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/api/rating/user/" + id, Rating[].class);
		log.info("{}", ratings);
		// http://localhost:9003/api/hotel/d80579b9-87ca-4b53-8fc6-072017fbd40a

		List<Rating> ratingList = Arrays.stream(ratings).toList();

		List<Rating> ratingsFor = ratingList.stream().map(rating -> {
			// ResponseEntity<Hotel> ratingsForHotel = restTemplate
			// .getForEntity("http://HOTELSERVICE/api/hotel/" + rating.getHotelId(),
			// Hotel.class);

			Hotel hotel = this.clientInf.getHotelId(rating.getHotelId());

			rating.setHotel(hotel);

			return rating;

		}).collect(Collectors.toList());

		user.setRatings(ratingsFor);
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
