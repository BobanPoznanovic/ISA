package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Reservation;
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
			return null;
		}

		@Override
		public List<Reservation> findAll(User user) {
			// TODO Auto-generated method stub
			return reservationRepository.findAll(user);
		}

		@Override
		public Reservation createNewReservation(Reservation reservation) {
			// TODO Auto-generated method stub
			return  reservationRepository.save(reservation);
		}


		@Override
		public Reservation update(Long id) {

			return null;
		}

		@Override
		public Reservation delete(Long id) {
			reservationRepository.delete(id);
			return null;
		}		

	}

