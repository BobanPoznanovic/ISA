package isa.project.service;

import java.util.ArrayList;
import java.util.List;

import isa.project.domain.Reservation;
import isa.project.domain.ReservationStatus;
import isa.project.domain.Term;
import isa.project.domain.User;

public interface ReservationService {
	
	Reservation findOne(Long id);
	
	List<Reservation> findAll();
	
	Reservation createNewReservation(Reservation reservation);
	
	Reservation delete(Long id);
	
	void update(Long id, ReservationStatus reservationStatus);

	Reservation findReservationByUser(User user, Term term);
	
	List<Reservation> findReservationsByUser(User user);
	
	ArrayList<Reservation> findWaitingReservations(ReservationStatus waiting);
	
	List<Reservation> findReservationByStatus(User user, ReservationStatus reservationStatus);
}

