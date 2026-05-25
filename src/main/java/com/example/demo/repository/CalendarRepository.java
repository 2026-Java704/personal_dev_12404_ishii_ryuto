package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Medicine;

public interface CalendarRepository extends JpaRepository<Medicine, Integer> {

}
