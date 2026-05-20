package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	List<Users> findByName(String name);

	List<Users> findByPassword(String password);

	List<Users> findByNameAndPassword(String name, String password);
}
