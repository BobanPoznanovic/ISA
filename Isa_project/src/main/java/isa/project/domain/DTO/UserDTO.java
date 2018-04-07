package isa.project.domain.DTO;

import isa.project.domain.User;

public class UserDTO {

	private String name;
	private String surname;
	private String password;
	private String confirmPassword;
	private String email;
	private String city;
	private String phone;
	
	public UserDTO(User user){
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.city = user.getCity();
		this.phone = user.getPhone();
	}
}
