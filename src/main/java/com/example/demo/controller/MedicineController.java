package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

		//		未ログインユーザーをログイン画面へ
		if (account.getId() == null) {
			return "redirect:/login";
		}

		//		ユーザーに応じた薬一覧の表示
		List<Medicine> medicineList = medicineRepository.findByUserIdOrderById(account.getId());
		model.addAttribute("medicineList", medicineList);

		return "medicine";
	}

	//	薬の新規登録画面の表示
	@GetMapping("/medicine/add")
	public String medicineShow(
			@RequestParam(required = false) LocalDate date,
			Model model) {

		//		未ログインユーザーをログイン画面へ
		if (account.getId() == null) {
			return "redirect:/login";
		}

		model.addAttribute("date", date);

		return "medicineAdd";
	}

	//	薬の新規登録処理
	@PostMapping("/medicine/add")
	public String medicineAdd(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "") Boolean mCheck,
			@RequestParam LocalDate date,
			@RequestParam(defaultValue = "") String dtime,
			Model model) {

		Users users = usersRepository.findById(account.getId()).get();

		Medicine medicine = new Medicine(name, note, count, mCheck, date, dtime, users);

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setDate(date);
		medicine.setNote(note);
		medicine.setmCheck(false);
		medicine.setDtime(dtime);

		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("お薬名は必須です");
		}
		if (count == null) {
			errorList.add("個数の入力は必須です");
		}
		if (date == null) {
			errorList.add("日付の入力は必須です");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "medicineAdd";
		}

		medicineRepository.save(medicine);

		return "redirect:/calendar/show";
	}

	//	薬の更新登録画面の表示
	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {

		//		未ログインユーザーをログイン画面へ
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Medicine medicine = medicineRepository.findById(id).get();

		List<String> dtimeList = new ArrayList<>();
		dtimeList.add("朝");
		dtimeList.add("昼");
		dtimeList.add("晩");

		model.addAttribute("dtimeList", dtimeList);

		// 他人の薬は編集画面を開けない
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

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
			@RequestParam(defaultValue = "") LocalDate date,
			@RequestParam(defaultValue = "") String dtime,
			Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		// 他人の薬は編集画面を開けない
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);
		medicine.setmCheck(mCheck);
		medicine.setDate(date);
		medicine.setDtime(dtime);

		medicineRepository.save(medicine);

		return "redirect:/";
	}

	//		削除処理
	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {

		//		未ログインユーザーをログイン画面へ
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Medicine medicine = medicineRepository.findById(id).get();
		// 他人の薬は編集画面を開けない
		if (!medicine.getUser().getId().equals(account.getId())) {
			return "redirect:/medicine";
		}

		medicineRepository.deleteById(id);

		return "redirect:/";
	}

	//	追加機能
	//	薬の検索
	@GetMapping("/medicine/search")
	public String search(
			@RequestParam(defaultValue = "") String name,
			Model model) {

		List<Medicine> medicineList = medicineRepository.findByNameContainingAndUserId(name, account.getId());
		model.addAttribute("medicineList", medicineList);

		return "medicine";
	}

}
