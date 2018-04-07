package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.domain.enumProps;
import isa.project.repositories.CinemaTheaterRepository;
import isa.project.service.CinemaTheaterService;


@Transactional
@Service
public class JpaCinemaTheaterImpl implements CinemaTheaterService{
	
	@Autowired
	private CinemaTheaterRepository cinemaTheaterRepository;
	
	@Override
	public List<CinemaTheater> findByType(enumProjection type) {
		// TODO Auto-generated method stub
			return cinemaTheaterRepository.findByType(type);
	}

	@Override
	public CinemaTheater findOne(Long id) {
		return cinemaTheaterRepository.findOne(id);
	}
	
	@Override
	public List<CinemaTheater> findAll() {
		return cinemaTheaterRepository.findAll();
	}

	@Override
	public CinemaTheater save(CinemaTheater cinemaTheater) {
		return cinemaTheaterRepository.save(cinemaTheater);
	}

	@Override
	public List<CinemaTheater> save(List<CinemaTheater> cinemaTheater) {
		return cinemaTheaterRepository.save(cinemaTheater);
	}

	@Override
	public CinemaTheater delete(Long id) {
		CinemaTheater cinemaTheater = cinemaTheaterRepository.findOne(id);
		if(cinemaTheater == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant country");
		}
		cinemaTheaterRepository.delete(cinemaTheater);
		return cinemaTheater;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}

	
	
}
