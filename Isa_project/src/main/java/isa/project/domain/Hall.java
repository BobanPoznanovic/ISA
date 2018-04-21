package isa.project.domain;

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
	@Column(name = "HALL_NAME")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	
	@Column(name = "TERM_SEATMAP", columnDefinition = "LONGBLOB"/*, columnDefinition = "varbinary(max)"*/)
	private byte[] seats;
	
	@Transient
	private SeatMap seatMap;

	public CinemaTheater getCinemaTheater() {
		return cinemaTheater;
	}

	public void setCinemaTheater(CinemaTheater cinemaTheater) {
		this.cinemaTheater = cinemaTheater;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public byte[] getSeats() {
		return seats;
	}

	public void setSeats(byte[] seats) {
		this.seats = seats;
	}

	public SeatMap getSeatMap() {
		return seatMap;
	}

	public void setSeatMap(SeatMap seatMap) {
		this.seatMap = seatMap;
	}

}