package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.Projection;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long>{

	public List<Projection> findByName(String name);
	
	public List<Projection> findById(Long id);
	
}
