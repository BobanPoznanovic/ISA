package isa.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	@Column(name="valid_to", unique=false, nullable=false)
	private String valid_to;
	@Column(name="photo", unique=false, nullable=true)
	private String photo;
	@Column(name="price", unique=false, nullable=false)
	private int price;
	@Column(name="approved", unique=false, nullable=false)
	private boolean approved;
	public Ad() {
		super();
	}
	public Ad(enumProps type, String name, String description, String valid_to, String photo, int price, boolean approved) {
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
	public String getValid_to() {
		return valid_to;
	}
	public void setValid_to(String valid_to) {
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
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
