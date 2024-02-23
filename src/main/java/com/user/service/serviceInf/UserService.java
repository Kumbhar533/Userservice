package com.user.service.serviceInf;

import java.util.List;

import com.user.service.entity.UserEntity;

public interface UserService {

	UserEntity addUser(UserEntity userEntity);

	UserEntity getUser(String id);

	List<UserEntity> getAllUsers();

}
