package isa.project.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
	@SuppressWarnings("unchecked")
	public Hall save(Hall Hall);
	
	@Query("SELECT h FROM Hall h WHERE h.cinemaTheater = :cinemaTheater")
	public List<Hall> findAll(CinemaTheater cinemaTheater);

	@Modifying(clearAutomatically = true)
    @Query(value = "update Hall h set h.seats = :seatMap WHERE h.id = :id")
    public void update(@Param("id") Long id, @Param("seatMap") byte[] seatMap);
}
