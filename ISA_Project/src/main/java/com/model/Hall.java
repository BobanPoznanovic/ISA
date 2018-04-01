package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "HALL")
public class Hall {
	
	@Id
	@GeneratedValue
	@Column(name = "HALL_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="CT_ID")
	private CinemaTheater cinemaTheater;
	
	@NotNull
	@Column(name = "HALL_ROWS", nullable = false)
	private int rows;
	
	@NotNull
	@Column(name = "HALL_COLUMNS", nullable = false)
	private int columns;
	
	@NotNull
	@Column(name = "HALL_SEATMAP", nullable = false)
	private byte[] seatMap;
	
	@Transient
	private SeatType[][] seatMapMatrix;
	
}