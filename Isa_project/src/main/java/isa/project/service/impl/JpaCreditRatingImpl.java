package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.CreditRrating;
import isa.project.repositories.CreditRatingRepository;
import isa.project.service.CreditRatingService;

@Transactional
@Service
public class JpaCreditRatingImpl implements CreditRatingService {

	@Autowired
	private CreditRatingRepository creditRatingRepository;
	
	@Override
	public CreditRrating findOne(Long id) {
		return creditRatingRepository.findOne(id);
	}
	
	@Override
	public List<CreditRrating> findAll() {
		return creditRatingRepository.findAll();
	}

	@Override
	public CreditRrating save(CreditRrating ad) {
		return creditRatingRepository.save(ad);
	}

	@Override
	public List<CreditRrating> save(List<CreditRrating> ad) {
		return creditRatingRepository.save(ad);
	}

	@Override
	public CreditRrating delete(Long id) {
		CreditRrating ad = creditRatingRepository.findOne(id);
		if(ad == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant country");
		}
		creditRatingRepository.delete(ad);
		return ad;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}

	
	
}
