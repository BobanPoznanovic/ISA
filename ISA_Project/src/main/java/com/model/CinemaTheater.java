package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "CINEMATHEATER")
public class CinemaTheater {
	
	@Id
	@GeneratedValue
	@Column(name = "CT_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Size(min = 2, max = 30)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@NotNull
	@Column(name = "CT_NAME", nullable = false)
	private String name;
	
	@Size(min = 3, max = 30)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z0-9]*")
	@NotNull
	@Column(name = "CT_ADRESS", nullable = false)
	private String adress;
	
	@Size(max = 200)
	@NotNull
	@Column(name = "CT_DESC", nullable = false)
	private String description;
	
	@Pattern(regexp = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$")
	@NotNull
	@Column(name="CT_EMAIL",unique=true ,nullable=false)
	private String email;

	@Enumerated(EnumType.STRING)
	private CTType type; 
}
