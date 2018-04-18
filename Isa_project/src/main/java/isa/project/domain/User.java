package isa.project.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Role userRole;
	
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	
	@Size(max = 30)
//	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="name", unique=false, nullable=false)
	private String name;
	
	@Size(max = 30)
//	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="surname", unique=false, nullable=false)
	private String surname;
	
	@GeneratedValue
	@NotNull
	@Column(name="USER_SALT",unique=true, nullable=false)
	private byte[] salt;
	
	@NotNull
	@Column(name="password", unique=false, nullable=false)
	private String password;
	
	@Size(min = 6, max = 50)
//	@Pattern(regexp = "^[\\\\w!#$%&â€™*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&â€™*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$")
	@NotNull
	@Column(name="email", unique=false, nullable=false)
	private String email;
	
//	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="city", unique=false, nullable=false)
	private String city;
	
//	@Pattern(regexp="^[0-9]*$")
	@NotNull
	@Column(name="phone", unique=false, nullable=false)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private CinemaTheater cinemaTheater;
	
	public CinemaTheater getCinemaTheater() {
		return cinemaTheater;
	}
	public void setCinemaTheater(CinemaTheater cinemaTheater) {
		this.cinemaTheater = cinemaTheater;
	}
	public Role getUserRole() {
		return userRole;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
