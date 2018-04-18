package isa.project.service;

import java.util.List;

import isa.project.domain.Projection;
import isa.project.domain.Term;

public interface TermService {
	
	Term findOne(Long id);
	
	List<Term> findAll(Projection projection);
	
	Term createNewTerm(Term term);
	
	Term delete(Long id);
	
	Term update(Long id);

	void update(Long termId, byte[] bs);

}
