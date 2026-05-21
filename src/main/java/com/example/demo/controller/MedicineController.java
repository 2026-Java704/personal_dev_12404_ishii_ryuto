package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.Users;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class MedicineController {

	private final MedicineRepository medicineRepository;
	private final UsersRepository usersRepository;
	private final Account account;

	public MedicineController(
			MedicineRepository medicineRepository,
			UsersRepository usersRepository,
			Account account) {
		this.medicineRepository = medicineRepository;
		this.usersRepository = usersRepository;
		this.account = account;
	}

	//	服薬画面の表示
	@GetMapping({ "/", "/medicine" })
	public String show(
			Model model) {

		List<Medicine> medicineList = medicineRepository.findByUserIdOrderById(account.getId());
		model.addAttribute("medicineList", medicineList);

		return "medicine";
	}

	//	薬の新規登録画面の表示
	@GetMapping("/medicine/add")
	public String medicineShow() {
		return "medicineAdd";
	}

	//	薬の新規登録処理
	@PostMapping("/medicine/add")
	public String medicineAdd(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "") Boolean mCheck,
			Model model) {

		Users users = usersRepository.findById(account.getId()).get();

		Medicine medicine = new Medicine(name, note, count, mCheck, users);
		medicineRepository.save(medicine);

		return "redirect:/";
	}

	//	薬の更新登録画面の表示
	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		model.addAttribute("medicine", medicine);

		return "medicineEdit";
	}

	//	薬の更新処理
	@PostMapping("/medicine/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "") Boolean mCheck,
			Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);
		medicine.setmCheck(mCheck);

		medicineRepository.save(medicine);

		return "redirect:/";
	}

	//		削除処理
	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {

		medicineRepository.deleteById(id);

		return "redirect:/";
	}

}
