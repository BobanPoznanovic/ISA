package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.Ad;
import isa.project.domain.enumProps;

@Repository
public interface FunZoneRepository extends JpaRepository<Ad, Long>{ 

	public List<Ad> findByApproved(boolean approved);
	public List<Ad> findByType(enumProps official);
	
}
