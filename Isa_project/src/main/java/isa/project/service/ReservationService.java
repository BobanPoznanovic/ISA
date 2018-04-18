package isa.project.service;

import java.util.List;

import isa.project.domain.Reservation;
import isa.project.domain.User;

public interface ReservationService {
	
	Reservation findOne(Long id);
	
	List<Reservation> findAll(User user);
	
	Reservation createNewReservation(Reservation reservation);
	
	Reservation delete(Long id);
	
	Reservation update(Long id);

}

