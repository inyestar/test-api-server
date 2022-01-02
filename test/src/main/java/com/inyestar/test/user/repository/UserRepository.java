package com.inyestar.test.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inyestar.test.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
	
	Optional<User> findByEmail(String email);
	
}
