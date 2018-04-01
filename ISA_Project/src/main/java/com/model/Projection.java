package com.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PROJECTION")
public class Projection {
	
	@Id
	@GeneratedValue
	@Column(name = "PROJECTION_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Size(min = 2, max = 30)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@NotNull
	@Column(name = "PROJECTION_NAME", unique = false, nullable = false)
	private String name;
	
	@Size(min = 2, max = 30)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@NotNull
	@Column(name = "PROJECTION_GENRE", nullable = false)
	private String genre;
	
	@Size(max = 50)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@Column(name = "PROJECTION_DIRECTOR")
	private String director;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="hh:mm")
	@NotNull
	@Column(name = "PROJECTION_DURATION", nullable = false)
	private Time duration;
	
	@Size(max = 200)
	@Column(name = "PROJECTION_ACTORS")
	private String actors;
	
	@Size(max = 100)
	@Pattern(regexp = "^[A-Z][a-z_ A-Z]*")
	@Column(name = "PROJECTION_POSTER")
	private String poster;
	
	@Size(max = 300)
	@Column(name = "PROJECTION_DESCRIPTION")
	private String description;

}

