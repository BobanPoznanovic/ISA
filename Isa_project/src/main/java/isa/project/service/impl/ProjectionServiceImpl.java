package isa.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Projection;
import isa.project.repositories.ProjectionRepository;
import isa.project.service.ProjectionService;

@Service
@Transactional
public class ProjectionServiceImpl implements ProjectionService{

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Override
	public Projection findOne(Long id) {
		// TODO Auto-generated method stub
		return projectionRepository.findOne(id);
	}

	@Override
	public List<Projection> findAll(CinemaTheater cinemaTheater) {
		// TODO Auto-generated method stub
		return projectionRepository.findAll(cinemaTheater);
	}

	@Override
	public Projection createNewProjection(Projection projection) {
		// TODO Auto-generated method stub
		return  projectionRepository.save(projection);
	}

	@Override
	public Projection delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projection update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
