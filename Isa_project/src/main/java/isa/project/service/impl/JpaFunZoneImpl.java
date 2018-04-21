package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Ad;
import isa.project.repositories.FunZoneRepository;
import isa.project.service.FunZoneService;

@Transactional
@Service
public class JpaFunZoneImpl implements FunZoneService {

	@Autowired
	private FunZoneRepository funZoneRepository;

	@Override
	public List<Ad> findByApproved(boolean approved) {
		// TODO Auto-generated method stub
			return funZoneRepository.findByApproved(approved);
	}
	
	@Override
	public Ad findOne(Long id) {
		return funZoneRepository.findOne(id);
	}
	
	@Override
	public List<Ad> findAll() {
		return funZoneRepository.findAll();
	}

	@Override
	public Ad save(Ad ad) {
		return funZoneRepository.save(ad);
	}

	@Override
	public List<Ad> save(List<Ad> ad) {
		return funZoneRepository.save(ad);
	}

	@Override
	public Ad delete(Long id) {
		Ad ad = funZoneRepository.findOne(id);
		if(ad == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant country");
		}
		funZoneRepository.delete(ad);
		return ad;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}

	
	
}
