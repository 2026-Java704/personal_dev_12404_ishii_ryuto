package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	List<Account> findByUsersName(String usersName);

	List<Account> findByUsersPassword(String usersPassword);

	List<Account> findByUsersNameAndUsersPassword(String usersName, String usersPassword);
}
