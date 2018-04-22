package isa.project.service;

import java.util.List;

import isa.project.domain.CreditRrating;

public interface CreditRatingService {
	
	CreditRrating findOne(Long id);
	
	List<CreditRrating> findAll();
	
	CreditRrating save(CreditRrating rating);
	
	List<CreditRrating> save(List<CreditRrating> rating);
	
	CreditRrating delete(Long id);

	void delete(List<Long> ids);
	
	
}
