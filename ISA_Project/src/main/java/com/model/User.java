package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {
	
	private static final long serialVersionUID = 2626562778387146532L;

	@Id
	@GeneratedValue
	@Column(name = "USER_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Size(min = 6, max = 50)
	@Pattern(regexp = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$")
	@NotNull
	@Column(name="USER_EMAIL",unique=true ,nullable=false)
	private String email;
	
	@Size(max = 30)
	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="USER_NAME",unique=false,nullable=false)
	private String userName;

	@Size(max = 30)
	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="USER_SURNAME", nullable=false)
	private String userSurname;
	
	@GeneratedValue
	@NotNull
	@Column(name="USER_SALT",unique=true, nullable=false)
	private byte[] salt;
	
	@NotNull
	@Column(name="USER_PASSHASH",unique=true, nullable=false)
	private byte[] passHash;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="mm.dd.yyyy") 
	@Column(name="USER_DATE")
	private Date dateOfBirth;
	
	@Pattern(regexp="^[A-Z][a-z A-Z]*")
	@NotNull
	@Column(name="USER_CITY")
	private String city;
	
	@Enumerated(EnumType.STRING)
	private UserType userRole;
	
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	


}
