package com.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TERM")
public class Term {
	
	@Id
	@GeneratedValue
	@Column(name = "TERM_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="HALL_ID")
	private Hall hall;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="PROJECTION_ID")
	private Projection projection;
	
	@NotNull
	@Column(name = "TERM_SEATMAP", nullable = false)
	private byte[] seatMap;
	
	@Transient
	private boolean[][] seatMapMatrix;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="mm.dd.yyyy") 
	@Column(name="TERM_DATE")
	private Date termDate;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="hh:mm") 
	@Column(name="TERM_TIME")
	private Time termTime;
	
	@NotNull
	@Column(name = "TERM_PR_REGULAR", nullable = false)
	private int priceRegular;

	@NotNull
	@Column(name = "TERM_PR_VIP", nullable = false)
	private int priceVip;

	@NotNull
	@Column(name = "TERM_PR_BALCONY", nullable = false)
	private int priceBalcony;
	
}

