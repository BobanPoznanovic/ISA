package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Projection;
import isa.project.repositories.ProjectionRepository;
import isa.project.service.ProjectionService;

@Transactional
@Service
public class ProjectionServiceImpl implements ProjectionService {

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Override
	public Projection findOne(Long id) {
		// TODO Auto-generated method stub
		return projectionRepository.findOne(id);
	}

	@Override
	public List<Projection> findAll() {
		// TODO Auto-generated method stub
		return projectionRepository.findAll();
	}

	@Override
	public Projection save(Projection projection) {
		// TODO Auto-generated method stub
		return projectionRepository.save(projection);
	}

	@Override
	public List<Projection> save(List<Projection> projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projection delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Projection> findByName(String name) {
		// TODO Auto-generated method stub
		return projectionRepository.findByName(name);
	}

	@Override
	public List<Projection> findById(CinemaTheater ct) {
		// TODO Auto-generated method stub
		return projectionRepository.findById(ct.getId());
	}
	
}
