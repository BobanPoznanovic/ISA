package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Hall;
import isa.project.domain.User;
import isa.project.repositories.HallRepository;
import isa.project.service.HallService;


@Transactional
@Service
public class HallServiceImpl implements HallService{

	@Autowired
	private HallRepository hallRepository;
	
	@Override
	public Hall findOne(Long id) {
		// TODO Auto-generated method stub
		return hallRepository.findOne(id);
	}

	@Override
	public Hall createNewHall(Hall hall) {
		// TODO Auto-generated method stub
		return hallRepository.save(hall);
	}

	@Override
	public Hall delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hall update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hall login(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hall> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}

