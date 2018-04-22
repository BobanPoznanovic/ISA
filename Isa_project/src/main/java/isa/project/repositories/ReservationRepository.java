package isa.project.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isa.project.domain.Reservation;
import isa.project.domain.ReservationStatus;
import isa.project.domain.Term;
import isa.project.domain.User;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	public List<Reservation> findAll();
	
	@SuppressWarnings("unchecked")
	public Reservation save(Reservation reservation);

	@Query("SELECT r FROM Reservation r WHERE r.user = :user and r.term=:term")
	public Reservation findReservationByUser(@Param("user") User user,@Param("term") Term term);
	
	@Query("SELECT r FROM Reservation r WHERE r.user = :user")
	public List<Reservation> findReservationsByUser(@Param("user") User user);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Reservation r SET r.reservationStatus = :reservationStatus WHERE r.id = :id")
	public void update(@Param("id") Long id, @Param("reservationStatus") ReservationStatus reservationStatus);
	
	@Query("SELECT r FROM Reservation r WHERE r.reservationStatus = :reservationStatus")
	public ArrayList<Reservation> findWaitingReservations(@Param("reservationStatus") ReservationStatus waiting);

	@Query("SELECT r FROM Reservation r WHERE r.user = :user and r.reservationStatus = :reservationStatus")
	public List<Reservation> findReservationByStatus(@Param("user") User user, @Param("reservationStatus") ReservationStatus reservationStatus);

}

