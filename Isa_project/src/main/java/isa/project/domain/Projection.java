package isa.project.domain;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Date duration;
	
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
	
	@NotNull
	@OneToOne
	@JoinColumn(name="CT_ID")
	private CinemaTheater cinemaTheater;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CinemaTheater getCinemaTheater() {
		return cinemaTheater;
	}

	public void setCinemaTheater(CinemaTheater cinemaTheater) {
		this.cinemaTheater = cinemaTheater;
	}
	
}

