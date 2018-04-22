package isa.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Skala")
public class CreditRrating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="forLogin", unique=false, nullable=false)
	private int forLogin;
	
	@Column(name="forBuyOfficialProp", unique=false, nullable=false)
	private int forBuyOfficialProp;
	
	@Column(name="forBuyUsedProp", unique=false, nullable=false)
	private int forBuyUsedProp;
	
	@Column(name="forSendOffer", unique=false, nullable=false)
	private int forSendOffer;

	public CreditRrating() {
		super();
	}

	public CreditRrating(int forLogin, int forBuyOfficialProp, int forBuyUsedProp, int forSendOffer) {
		super();
		this.forLogin = forLogin;
		this.forBuyOfficialProp = forBuyOfficialProp;
		this.forBuyUsedProp = forBuyUsedProp;
		this.forSendOffer = forSendOffer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getForLogin() {
		return forLogin;
	}

	public void setForLogin(int forLogin) {
		this.forLogin = forLogin;
	}

	public int getForBuyOfficialProp() {
		return forBuyOfficialProp;
	}

	public void setForBuyOfficialProp(int forBuyOfficialProp) {
		this.forBuyOfficialProp = forBuyOfficialProp;
	}

	public int getForBuyUsedProp() {
		return forBuyUsedProp;
	}

	public void setForBuyUsedProp(int forBuyUsedProp) {
		this.forBuyUsedProp = forBuyUsedProp;
	}

	public int getForSendOffer() {
		return forSendOffer;
	}

	public void setForSendOffer(int forSendOffer) {
		this.forSendOffer = forSendOffer;
	}
	
	
}
