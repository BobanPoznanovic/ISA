package com.model;


import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PROP")
public class Prop {
	
	@Id
	@GeneratedValue
	@Column(name = "PROP_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User publisher;
	
	@Enumerated(EnumType.STRING)
	private PropType type;
	
	@Enumerated(EnumType.STRING)
	private PropStatus status;
	
	@Size(min = 3, max = 30)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@NotNull
	@Column(name = "PROP_NAME", nullable = false)
	private String name;

	@NotNull
	@Column(name = "PROP_DESCRIPTION", nullable = false)
	private String description;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="mm.dd.yyyy") 
	@Column(name="EXP_DATE")
	private Date expiryDate;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="hh:mm") 
	@Column(name="EXP_TIME")
	private Time expiryTime;
	
	@NotNull
	@Column(name = "PROP_PHOTO")
	private String photo;
	
	@NotNull
	@Column(name = "PROP_PRICE")
	private int currPrice;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User leadingUser;
	
}
