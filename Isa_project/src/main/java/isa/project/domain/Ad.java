package isa.project.domain;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Oglas")
public class Ad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tip", unique=false, nullable=false)
	private enumProps type;
	@Column(name="name", unique=false, nullable=false)
	private String name;
	@Column(name="description", unique=false, nullable=false)
	private String description;
	@Column(name="valid_to", unique=false)
	private Date valid_to;
	@Column(name="photo", unique=false, nullable=true)
	private String photo;
	@Column(name="price", unique=false, nullable=false)
	private int price;
	@Column(name="approved", unique=false)
	private Boolean approved;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ad")
	@JsonIgnore
    private List<Offer> offers = new ArrayList<Offer>();
	
	@ManyToOne
	private User creator;
	
	public Ad() {
		super();
	}
	public Ad(enumProps type, String name, String description, Date valid_to, String photo, int price, boolean approved) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
		this.valid_to = valid_to;
		this.photo = photo;
		this.price = price;
		this.approved = approved;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public enumProps getType() {
		return type;
	}
	public void setType(enumProps type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getValid_to() {
		return valid_to;
	}
	public void setValid_to(Date valid_to) {
		this.valid_to = valid_to;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Boolean isApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	
}
