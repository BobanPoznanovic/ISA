package isa.project.service;

import java.util.List;

import isa.project.domain.Ad;
import isa.project.domain.Offer;

public interface OfferService  {

	Offer findOne(Long id);
	
	List<Offer> findAll();
	
	Offer save(Offer offer);
	
	List<Offer> save(List<Offer> offers);
	
	Offer delete(Long id);

	//void delete(List<Long> ids);
		
	public List<Offer> findByAdId(Long id);
}