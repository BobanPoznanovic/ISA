package isa.project.service;

import java.util.List;

import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.Term;

public interface TermService {
	
	Term findOne(Long id);
	
	List<Term> findAll(Projection projection);
	
	Term createNewTerm(Term term);
	
	Term delete(Long id);
	
	Term update(Long id);

	void update(Long termId, byte[] bs);

	List<String> findDatesByProjection(Projection projection);
	
	List<Term> findTermByProjectionDate(Projection projection, String termDate);
	
	List<String> findTimesByProjectionDate(Projection projection, String termDate);
	
	List<Hall> findHallByProjDateTime(Projection projection, String termDate, String termTime);

	byte[] findSeats(Projection projection, String termDate, String termTime, Hall hall);
	
	Term findTerm(Projection projection, String termDate, String termTime, Hall hall);
}
