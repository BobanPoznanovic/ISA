package isa.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Projection;
import isa.project.domain.Reservation;
import isa.project.domain.ReservationStatus;
import isa.project.domain.SeatMap;
import isa.project.domain.SeatType;
import isa.project.domain.Term;
import isa.project.domain.User;
import isa.project.domain.enumProjection;
import isa.project.service.CinemaTheaterService;
import isa.project.service.ProjectionService;
import isa.project.service.ReservationService;
import isa.project.service.TermService;

@RestController
@RequestMapping(value="/reservation")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired 
	private TermService termService;
	
	@Autowired 
	private ProjectionService projectionService;
	
	@Autowired
	private CinemaTheaterService cinemaTheaterService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value="/getCinemas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CinemaTheater>> getCinemas() {

		List<CinemaTheater> cinemas = cinemaTheaterService.findByType(enumProjection.valueOf("CINEMA"));
		return new ResponseEntity<List<CinemaTheater>>(cinemas, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getCinemas/{cinemaId}/getProjections", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Projection>> getProjectionsForCinema(@PathVariable Long cinemaId) {
		CinemaTheater cinemaTheater = cinemaTheaterService.findOne(cinemaId);	
		List<Projection> projections = projectionService.findAll(cinemaTheater);
		
		return new ResponseEntity<List<Projection>>(projections, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getProjection/{projectionId}/getTerms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Term>> getTermsForProjection(@PathVariable Long projectionId) {
		Projection projection = projectionService.findOne(projectionId);	
		List<Term> terms = termService.findAll(projection);
		
		return new ResponseEntity<List<Term>>(terms, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reserve", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> makeReservation(@RequestBody Reservation reservation,@RequestBody ArrayList<Integer> rows, @RequestBody ArrayList<Integer> cols, @RequestBody ArrayList<User> friends) throws ClassNotFoundException, IOException{

		if(checkSeats(rows, cols, reservation.getTerm())==false) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		reservation.setUser((User)httpSession.getAttribute("user"));
		SeatMap currSeatMap = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getSeats());
		currSeatMap.getFreeSeats()[rows.get(0)][cols.get(0)]=false;
		termService.update(reservation.getTerm().getId(),SeatMap.convertToBytes(currSeatMap));
		
		SeatMap seatTypes = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getHall().getSeats());
		SeatType type = seatTypes.getSeatTypes()[rows.get(0)][cols.get(0)];
		int price;
		if(type==SeatType.REGULAR) {
			price = reservation.getTerm().getPriceRegular();
		}
		else if (type==SeatType.BALCONY) {
			price = reservation.getTerm().getPriceBalcony();
		}
		else {
			price = reservation.getTerm().getPriceVip();
		}
		reservation.setPrice(price);
		reservation.setReservationStatus(ReservationStatus.CONFIRMED);
		reservation.setTimeOfReservation(new Date());
		
		reservationService.createNewReservation(reservation);
		
		if(friends.size()>0) {
			for(int i=1;i<rows.size();i++) {
				reservation.setUser(friends.get(i-1));
				currSeatMap = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getSeats());
				currSeatMap.getFreeSeats()[rows.get(i)][cols.get(i)]=false;
				termService.update(reservation.getTerm().getId(),SeatMap.convertToBytes(currSeatMap));
				
				seatTypes = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getHall().getSeats());
				type = seatTypes.getSeatTypes()[rows.get(i)][cols.get(i)];
				if(type==SeatType.REGULAR) {
					price = reservation.getTerm().getPriceRegular();
				}
				else if (type==SeatType.BALCONY) {
					price = reservation.getTerm().getPriceBalcony();
				}
				else {
					price = reservation.getTerm().getPriceVip();
				}
				reservation.setPrice(price);
				reservation.setReservationStatus(ReservationStatus.WAITING);
				
				reservationService.createNewReservation(reservation);
			}
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	@RequestMapping(value = "/cancel/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> makeReservation(@PathVariable Long id) throws ClassNotFoundException, IOException{
		Reservation reservation = reservationService.findOne(id);
		Date date = new Date();
		if(reservation.getTerm().getTermDate().equals(DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH))) {
			if (DateUtils.addMinutes(date, 30).after(reservation.getTerm().getTermTime())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		SeatMap currSeatMap = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getSeats());
		currSeatMap.getFreeSeats()[reservation.getRow()][reservation.getColumn()]=true;
		termService.update(reservation.getTerm().getId(),SeatMap.convertToBytes(currSeatMap));	
		
		reservationService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public boolean checkSeats(ArrayList<Integer> rows, ArrayList<Integer> cols, Term term) throws ClassNotFoundException, IOException {
		SeatMap currSeatMap = (SeatMap) SeatMap.convertToSeatMap(term.getSeats());
		for(int i=0;i<rows.size();i++){
			if(currSeatMap.getFreeSeats()[rows.get(i)][cols.get(i)]==false) {
				return false;
			}
		}
		return true;
	}
}
