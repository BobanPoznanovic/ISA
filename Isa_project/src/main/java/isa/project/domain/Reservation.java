package isa.project.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import javax.persistence.Temporal;

@Entity
@Table(name = "RESERVATION")
public class Reservation {
	
	@Id
	@GeneratedValue
	@Column(name = "RESERVATION_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "TIME_OF_RESERVATION")
	private Date timeOfReservation;
	
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
	
	public Date getTimeOfReservation() {
		return timeOfReservation;
	}

	public void setTimeOfReservation(Date timeOfReservation) {
		this.timeOfReservation = timeOfReservation;
	}

	@NotNull
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getVisitRating() {
		return visitRating;
	}

	public void setVisitRating(int visitRating) {
		this.visitRating = visitRating;
	}

	public int getProjectionRating() {
		return projectionRating;
	}

	public void setProjectionRating(int projectionRating) {
		this.projectionRating = projectionRating;
	}	

}
