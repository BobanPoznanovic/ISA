package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.Term;
import isa.project.repositories.TermRepository;
import isa.project.service.TermService;

@Transactional
@Service
public class TermServiceImpl implements TermService{

	@Autowired
	private TermRepository termRepository;
	
	@Override
	public Term findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Term> findAll(Projection projection) {
		// TODO Auto-generated method stub
		return termRepository.findAll();
	}

	@Override
	public Term createNewTerm(Term term) {
		// TODO Auto-generated method stub
		return  termRepository.save(term);
	}

	@Override
	public Term delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Long termId, byte[] seatMap) {
		termRepository.update(termId, seatMap);
		return;
	}

	@Override
	public Term update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findDatesByProjection(Projection projection) {
		// TODO Auto-generated method stub
		return termRepository.findDatesByProjection(projection);
	}

	@Override
	public List<Term> findTermByProjectionDate(Projection projection, String termDate) {
		// TODO Auto-generated method stub
		return termRepository.findTermByProjectionDate(projection, termDate);
	}

	@Override
	public List<String> findTimesByProjectionDate(Projection projection, String termDate) {
		// TODO Auto-generated method stub
		return termRepository.findTimesByProjectionDate(projection, termDate);
	}

	@Override
	public List<Hall> findHallByProjDateTime(Projection projection, String termDate, String termTime) {
		// TODO Auto-generated method stub
		return termRepository.findHallByProjDateTime(projection, termDate, termTime);
	}

	@Override
	public byte[] findSeats(Projection projection, String termDate, String termTime, Hall hall) {
		// TODO Auto-generated method stub
		return termRepository.findSeats(projection, termDate, termTime, hall);
	}

	@Override
	public Term findTerm(Projection projection, String termDate, String termTime, Hall hall) {
		// TODO Auto-generated method stub
		return termRepository.findTerm(projection, termDate, termTime, hall);
	}
	
	

}
