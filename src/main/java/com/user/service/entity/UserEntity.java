package com.user.service.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_entity")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "Name", length = 30)
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "About")
	private String about;

	@Transient
	private List<Rating> ratings;

}
