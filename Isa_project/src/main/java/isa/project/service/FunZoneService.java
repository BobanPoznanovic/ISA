package isa.project.service;

import java.util.List;

import isa.project.domain.Ad;
import isa.project.domain.enumProps;

public interface FunZoneService {

	Ad findOne(Long id);
	
	List<Ad> findAll();
	
	Ad save(Ad ad);
	
	List<Ad> save(List<Ad> ad);
	
	Ad delete(Long id);

	void delete(List<Long> ids);
	
	public List<Ad> findByApproved(boolean approved);
	
	public List<Ad> findByType(enumProps official);
}
