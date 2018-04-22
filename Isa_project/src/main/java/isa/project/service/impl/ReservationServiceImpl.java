package isa.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Reservation;
import isa.project.domain.ReservationStatus;
import isa.project.domain.Term;
import isa.project.domain.User;
import isa.project.repositories.ReservationRepository;
import isa.project.service.ReservationService;

@Transactional
@Service
public class ReservationServiceImpl implements ReservationService {

		@Autowired
		private ReservationRepository reservationRepository;
		
		
		@Override
		public Reservation findOne(Long id) {
			// TODO Auto-generated method stub
			return reservationRepository.findOne(id);
		}

		@Override
		public List<Reservation> findAll() {
			// TODO Auto-generated method stub
			return reservationRepository.findAll();
		}

		@Override
		public Reservation createNewReservation(Reservation reservation) {
			// TODO Auto-generated method stub
			return  reservationRepository.save(reservation);
		}

		/*@Override
		public Reservation update(Long id) {

			return null;
		}*/

		@Override
		public Reservation delete(Long id) {
			reservationRepository.delete(id);
			return null;
		}

		@Override
		public Reservation findReservationByUser(User user, Term term) {
			// TODO Auto-generated method stub
			return reservationRepository.findReservationByUser(user, term);
		}

		@Override
		public List<Reservation> findReservationsByUser(User user) {
			// TODO Auto-generated method stub
			return reservationRepository.findReservationsByUser(user);
		}

		@Override
		public void update(Long id, ReservationStatus reservationStatus) {
			// TODO Auto-generated method stub
			reservationRepository.update(id, reservationStatus);
		}

		@Override
		public ArrayList<Reservation> findWaitingReservations(ReservationStatus waiting) {
			// TODO Auto-generated method stub
			return reservationRepository.findWaitingReservations(waiting);
		}

		@Override
		public List<Reservation> findReservationByStatus(User user, ReservationStatus reservationStatus) {
			// TODO Auto-generated method stub
			return reservationRepository.findReservationByStatus(user, reservationStatus);
		}		

	}

