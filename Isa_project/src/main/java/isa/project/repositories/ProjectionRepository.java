package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Projection;
import isa.project.domain.User;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {
	@SuppressWarnings("unchecked")
	public Projection save(Projection projection);
	
	public Projection findOne(Long id);
	
	@Query("SELECT p FROM Projection p WHERE p.cinemaTheater = :cinemaTheater")
	public List<Projection> findAll(CinemaTheater cinemaTheater);
	
	
}
