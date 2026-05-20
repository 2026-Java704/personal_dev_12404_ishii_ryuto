package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	//	薬の新規登録画面の表示
	@GetMapping("/medicine/add")
	public String medicineShow() {
		return "medicineAdd";
	}

	@PostMapping("/medicine/add")
	public String medicineAdd(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "") Boolean mCheck,
			Model model) {

		Medicine medicine = new Medicine(name, note, count, mCheck);
		medicineRepository.save(medicine);

		return "redirect:/";
	}
}
