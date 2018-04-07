package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.domain.enumProps;

@Repository
public interface CinemaTheaterRepository extends JpaRepository<CinemaTheater, Long>{

	public List<CinemaTheater> findByType(enumProjection type);
}
