package com.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.service.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
