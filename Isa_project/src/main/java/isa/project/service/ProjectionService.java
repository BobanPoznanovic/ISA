package isa.project.service;

import java.util.List;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Projection;

public interface ProjectionService {
		
	Projection findOne(Long id);
	
	List<Projection> findAll(CinemaTheater cinemaTheater);
	
	Projection createNewProjection(Projection projection);
		
	Projection delete(Long id);
		
	Projection update(Long id);

}
