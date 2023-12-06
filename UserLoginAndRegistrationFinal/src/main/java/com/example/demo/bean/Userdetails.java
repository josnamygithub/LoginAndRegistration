package com.example.demo.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.common.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_details")
public class Userdetails {
	
	@Id 
	//@JsonIgnore
	private int id;
	
	@NotNull(message = "User Id must not be null")
    //@NotBlank(message = "User Id must not be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "User ID must be a number")
	@Column(name ="user_id")
	private int userid;
	
	@NotNull(message = "Password must not be null")
   // @NotBlank(message = "Password must not be empty")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_=+]{8,}$",
    message = "Password must contain at least 8 characters including letters, numbers, and special characters")
	@Column(name ="password")
	private String password;
	
	@NotNull(message = "Email must not be null")
    //@NotBlank(message = "Email must not be empty")
    @Email(message = "Please provide a valid email address")
	@Column(name ="email")
	private String email;

	@NotNull(message = "First Name must not be null")
    //@NotBlank(message = "First Name must not be empty")
    @Size(min = 2, message = "Name must have at least 2 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only characters") 
	@Column(name ="first_name")
	private String firstname;
	
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only characters") 
	@Column(name ="last_name")
	private String lastname;
	
    private String phone_number;
	private String user_type = Constant.USER_TYPE.NORMAL;
	private Boolean is_active = true;
	private String sso_type;
	
	public Userdetails() {
		
	
	}

	public Userdetails(int id,
			@NotNull(message = "User Id must not be null") @Digits(integer = 2147483647, fraction = 0, message = "User ID must be a number") int userid,
			@NotNull(message = "Password must not be null") @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_=+]{8,}$", message = "Password must contain at least 8 characters including letters, numbers, and special characters") String password,
			@NotNull(message = "Email must not be null") @Email(message = "Please provide a valid email address") String email,
			@NotNull(message = "First Name must not be null") @Size(min = 2, message = "Name must have at least 2 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only characters") String firstname,
			@Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only characters") String lastname,
			String phone_number, String user_type, Boolean is_active, String sso_type) {
		super();
		this.id = id;
		this.userid = userid;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone_number = phone_number;
		this.user_type = user_type;
		this.is_active = is_active;
		this.sso_type = sso_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public String getSso_type() {
		return sso_type;
	}

	public void setSso_type(String sso_type) {
		this.sso_type = sso_type;
	}

	@Override
	public String toString() {
		return "Userdetails [id=" + id + ", userid=" + userid + ", password=" + password + ", email=" + email
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", phone_number=" + phone_number
				+ ", user_type=" + user_type + ", is_active=" + is_active + ", sso_type=" + sso_type + "]";
	}
	
	
}