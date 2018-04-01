package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RESERVATION")
public class Reservation {
	
	@Id
	@GeneratedValue
	@Column(name = "RESERVATION_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="TERM_ID")
	private Term term;
	
	@NotNull
	@Column(name = "RESERVATION_ROW", nullable = false)
	private int row;
	
	@NotNull
	@Column(name = "RESERVATION_COLUMN", nullable = false)
	private int column;

	@NotNull
	@Column(name = "RESERVATION_PRICE", nullable = false)
	private int price;
	
	@Column(name = "VISIT_RATING")
	private int visitRating;
	
	@Column(name = "PROJECTION_RATING")
	private int projectionRating;

}
