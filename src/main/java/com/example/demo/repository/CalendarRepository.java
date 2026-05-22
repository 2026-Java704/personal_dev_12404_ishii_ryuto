package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Medicine;

public interface CalendarRepository extends JpaRepository<Medicine, Integer> {

	//	まずはカレンダーに渡すidと薬の名前と飲んだか飲んでないかの要素のDBを作ってRepositoryの変数に入れる
}
