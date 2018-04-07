package isa.project.service;

import java.util.List;

import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.domain.enumProps;

public interface CinemaTheaterService {

	CinemaTheater findOne(Long id);
	
	List<CinemaTheater> findAll();
	
	CinemaTheater save(CinemaTheater cinemaTheater);
	
	List<CinemaTheater> save(List<CinemaTheater> cinemasTheaters);
	
	CinemaTheater delete(Long id);

	void delete(List<Long> ids);
	
	public List<CinemaTheater> findByType(enumProjection type);
	
}
