package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Medicine;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;

@Controller
public class MedicineController {

	private final MedicineRepository medicineRepository;
	private final Account account;

	public MedicineController(
			MedicineRepository medicineRepository,
			Account account) {
		this.medicineRepository = medicineRepository;
		this.account = account;
	}

	//	服薬画面の表示
	@GetMapping({ "/", "/medicine" })
	public String show(
			Model model) {

		List<Medicine> medicineList = medicineRepository.findByUserId(account.getId());
		model.addAttribute("medicineList", medicineList);

		return "medicine";
	}

}
