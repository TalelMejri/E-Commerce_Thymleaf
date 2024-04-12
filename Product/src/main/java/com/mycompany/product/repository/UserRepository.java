package com.mycompany.product.repository;

import com.mycompany.product.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	@Query(value="select * from users where email=:email",nativeQuery=true)
	User getUserByemail(String email);
	
	@Query(value="select * from users where role='Client'",nativeQuery=true)
	List<User> getUsers();
}
