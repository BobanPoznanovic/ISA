package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.project.domain.Ad;

public interface FunZoneRepository extends JpaRepository<Ad, Long>{ 

	public List<Ad> findByApproved(boolean approved);
	
}
