package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isa.project.domain.Reservation;
import isa.project.domain.User;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	@SuppressWarnings("unchecked")
	public Reservation save(Reservation reservation);
	
	@Query("SELECT r FROM Reservation r WHERE r.user = :user")
	public List<Reservation> findAll(User user);
	
}
