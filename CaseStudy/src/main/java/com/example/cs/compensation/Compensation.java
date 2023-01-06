package com.example.cs.compensation;

import java.sql.Date;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "compensation")
public class Compensation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Date datec;

	public int id_employee;

	public Compensation(int id, String type, double amount, String description, Date datec, int id_employee) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.description = description;
		this.datec = datec;
		this.id_employee = id_employee;
	}

	public Compensation() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDatec() {
		return datec;
	}

	public void setDatec(Date datec) {
		this.datec = datec;
	}

	public int getId_employee() {
		return id_employee;
	}

	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}

	public Optional<Compensation> findById(Long idCompensation) {
		return null;
	}

}

