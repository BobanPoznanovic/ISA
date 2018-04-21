package isa.project.domain.DTO;

import isa.project.domain.FriendshipStatus;
import isa.project.domain.User;

public class UserDTO {

	private String name;
	private String surname;
	private String email;
	private String city;
	private String phone;
	private FriendshipStatus frStatus;
	
	public UserDTO(String name, String surname, String email, String city, String phone, FriendshipStatus status) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.city = city;
		this.phone = phone;
		this.frStatus = status;
	}
	
	public UserDTO(User user)
	{
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.city = user.getCity();
		this.phone = user.getPhone();
	}

	public UserDTO(User user, FriendshipStatus status)
	{
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.city = user.getCity();
		this.phone = user.getPhone();
		this.frStatus = status;
	}
	
	public FriendshipStatus getStatus() {
		return frStatus;
	}

	public void setStatus(FriendshipStatus status) {
		this.frStatus = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
