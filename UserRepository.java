package com.vivek.Vivek.Ecommerce.project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.Vivek.Ecommerce.project.Entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
}
