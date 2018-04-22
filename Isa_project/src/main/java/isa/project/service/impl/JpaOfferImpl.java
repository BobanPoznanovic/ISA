package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Offer;
import isa.project.repositories.OfferRepository;
import isa.project.service.OfferService;

@Transactional
@Service
public class JpaOfferImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;

	@Override
	public Offer findOne(Long id) {
		return offerRepository.findOne(id);
	}
	
	@Override
	public List<Offer> findAll() {
		return offerRepository.findAll();
	}

	@Override
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public List<Offer> save(List<Offer> offer) {
		return offerRepository.save(offer);
	}

	@Override
	public Offer delete(Long id) {
		Offer offer = offerRepository.findOne(id);
		if(offer == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant offer");
		}
		offerRepository.delete(offer);
		return offer;
	}
/*
	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}*/

	@Override
	public List<Offer> findByAdId(Long id){
		return offerRepository.findByAdId(id);
	}
	
}
