package isa.project.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="CinemasAndTheaters")
public class CinemaTheater {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(name="tip", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private enumProjection type;
	@Column(name="name", unique=false, nullable=false)
	private String name;
	@Column(name="adress", unique=false, nullable=false)
	private String adress;
	@Column(name="description", unique=false, nullable=false)
	private String description;
	@Column(name="rating", unique=false, nullable=false)
	private float rating;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cinemaTheater", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<User> cinemaTheaterAdmin;
	
	public CinemaTheater() {
		super();
	}
	public CinemaTheater(enumProjection type, String name, String adress, String description,
			float rating) {
		super();
		this.type = type;
		this.name = name;
		this.adress = adress;
		this.description = description;
		this.rating = rating;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public enumProjection getType() {
		return type;
	}
	public void setType(enumProjection type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public List<User> getCinemaTheaterAdmin() {
		return cinemaTheaterAdmin;
	}
	public void setCinemaTheaterAdmin(List<User> cinemaTheaterAdmin) {
		this.cinemaTheaterAdmin = cinemaTheaterAdmin;
	}

	
	
}

