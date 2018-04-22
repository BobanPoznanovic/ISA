package isa.project.domain;

import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

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

import isa.project.domain.Hall;
import isa.project.domain.Projection;

@Entity
@Table(name = "TERM")
public class Term {
	
	@Id
	@GeneratedValue
	@Column(name = "TERM_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="HALL_ID")
	private Hall hall;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="PROJECTION_ID")
	private Projection projection;
	
//	binary nije dovoljno velik inicijalno
	@Column(name = "SEATMAP", columnDefinition = "VARBINARY(MAX)"/* , columnDefinition = "varbinary(max)"*/)
	private byte[] seats;
	
	@Transient
	private SeatMap seatMap;
	
	@Column(name="TERM_DATE")
	private String termDate;
	
	@Column(name="TERM_TIME")
	private String termTime;

	@NotNull
	@Column(name = "TERM_PR_REGULAR", nullable = false)
	private int priceRegular;

	@NotNull
	@Column(name = "TERM_PR_VIP", nullable = false)
	private int priceVip;

	@NotNull
	@Column(name = "TERM_PR_BALCONY", nullable = false)
	private int priceBalcony;

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
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

	public String getTermDate() {
		return termDate;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}

	public String getTermTime() {
		return termTime;
	}

	public void setTermTime(String termTime) {
		this.termTime = termTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPriceRegular() {
		return priceRegular;
	}

	public void setPriceRegular(int priceRegular) {
		this.priceRegular = priceRegular;
	}

	public int getPriceVip() {
		return priceVip;
	}

	public void setPriceVip(int priceVip) {
		this.priceVip = priceVip;
	}

	public int getPriceBalcony() {
		return priceBalcony;
	}

	public void setPriceBalcony(int priceBalcony) {
		this.priceBalcony = priceBalcony;
	}

	public Long getId() {
		return id;
	}
	
}

