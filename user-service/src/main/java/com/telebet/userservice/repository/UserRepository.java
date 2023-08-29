package com.telebet.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telebet.userservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findUserByUserNameAndPassword(String userName, String password);
}
