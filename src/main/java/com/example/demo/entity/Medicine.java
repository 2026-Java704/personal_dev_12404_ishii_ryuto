package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 商品ID
	private String name; // カテゴリーID
	private String note; // 価格
	private Integer count;
	@Column(name = "m_check")
	private Boolean mCheck = false;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users user;

	//	ゲッターセッターの作成
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getmCheck() {
		return mCheck;
	}

	public void setmCheck(Boolean mCheck) {
		this.mCheck = mCheck;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	//	デフォルトコンストラクタ
	public Medicine() {

	}

	//	薬の新規追加のコンストラクタ
	public Medicine(String name, String note, Integer count, Boolean mCheck) {
		if (mCheck == null) {
			this.name = name;
			this.note = note;
			this.count = count;
			mCheck = false;
		}
	}

}