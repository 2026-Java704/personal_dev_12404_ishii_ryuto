package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Users;
import com.example.demo.model.Account;
import com.example.demo.repository.UsersRepository;

@Controller
public class UsersController {

	private final HttpSession session;
	private final UsersRepository usersRepository;
	private final Account account;

	public UsersController(
			HttpSession session,
			UsersRepository usersRepository,
			Account account) {
		this.session = session;
		this.usersRepository = usersRepository;
		this.account = account;
	}

	//	ログイン画面表示
	@GetMapping({ "/login", "/logout" })
	public String index() {
		//		セッション情報のクリア
		session.invalidate();

		return "users";
	}

	//	ログインの実行
	@PostMapping("/login")
	public String login(
			@RequestParam(defaultValue = "") Integer id,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		//		エラー処理
		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("ユーザー名は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "users";
		}

		//		DBのデータと一致した場合
		List<Users> usersList = usersRepository.findByNameAndPassword(name, password);
		if (usersList != null && usersList.size() > 0) {

			Users users = usersList.get(0);

			account.setName(users.getName());
			account.setId(users.getId());

			return "redirect:/medicine";
		}

		//		DBのデータと一致しない時の表示
		errorList.add("ユーザー名またはパスワードが違います");
		model.addAttribute("errorList", errorList);

		return "users";
	}

	//	ユーザーの新規登録画面表示
	@GetMapping("/users/add")
	public String ahowadd() {
		return "usersAdd";
	}

	//	ユーザーの新規登録
	@PostMapping("/users/add")
	public String add(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("ユーザー名は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "usersAdd";
		}

		Users users = new Users(name, password);
		usersRepository.save(users);

		return "redirect:/login";

	}

}
