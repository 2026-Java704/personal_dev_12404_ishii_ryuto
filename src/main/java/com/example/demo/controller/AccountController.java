package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Controller
public class AccountController {

	private final HttpSession session;
	private final AccountRepository accountRepository;

	public AccountController(HttpSession session, AccountRepository accountRepository) {
		this.session = session;
		this.accountRepository = accountRepository;
	}

	//	ログイン画面表示
	@GetMapping("/login")
	public String index() {
		//		セッション情報のクリア
		session.invalidate();

		return "account";
	}

	//	ログインの実行
	@PostMapping("/login")
	public String login(
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
			return "account";
		}

		List<Account> accountList = accountRepository.findByUsersNameAndUsersPassword(name, password);
		if (accountList != null && accountList.size() > 1) {
			return "redirect:/medicine";
		}

		errorList.add("ユーザー名またはパスワードが違います");
		model.addAttribute("errorList", errorList);

		return "account";
	}

	//	ユーザーの新規登録画面表示
	@GetMapping("/account/add")
	public String add() {
		return "accountAdd";
	}

	//	服薬画面の表示
	@GetMapping({ "/", "/medicine" })
	public String show() {
		return "medicine";
	}
}
