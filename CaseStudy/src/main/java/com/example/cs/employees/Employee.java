package com.example.cs.employees;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")

public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//If I don't add this annotation to the field, then it will show the variable name which is "firstName"
	@Column(name = "firstName", nullable = false)
	private String firstName;
	@Column(name = "middleName", nullable = false)
	private String middleName;
	@Column(name = "lastName", nullable = false)
	private String lastName;
	@Column(name = "birthDate", nullable = false)
	private Date birthDate;
	@Column(name = "position", nullable = false)
	private String position;
	
	public Employee() {
	}
	
	public Employee(String firstName, String middleName, String lastName, Date birthDate, String position) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.position = position;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}

