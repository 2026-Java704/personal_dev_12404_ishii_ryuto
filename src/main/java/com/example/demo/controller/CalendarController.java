
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Medicine;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;

@Controller
public class CalendarController {

	private final MedicineRepository medicineRepository;
	private final Account account;

	public CalendarController(MedicineRepository medicineRepository, Account account) {
		this.medicineRepository = medicineRepository;
		this.account = account;
	}

	@GetMapping("/calendar/show")
	public String calendar(Model model) {

		List<Medicine> medicineList = medicineRepository.findByUserIdOrderById(account.getId());

		List<Map<String, String>> calendarEvents = new ArrayList<>();

		for (Medicine medic : medicineList) {

			// 日付がないデータはカレンダーに出せない
			if (medic.getDate() == null) {
				continue;
			}

			String status = "未服薬";

			if (medic.getmCheck()) {
				status = "服薬済";
			}

			Map<String, String> event = new HashMap<>();
			event.put("title", medic.getName() + "：" + medic.getDtime() + "：" + status);
			event.put("start", medic.getDate().toString());

			calendarEvents.add(event);
		}

		model.addAttribute("calendarEvents", calendarEvents);

		return "calendar";
	}
}