package isa.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.domain.enumProps;
import isa.project.service.CinemaTheaterService;

@RestController
@RequestMapping(value = "isa/cinemaTheater")
public class CinemaTheaterController {

	@Autowired
	private CinemaTheaterService cinemaTheaterService;
	
	@RequestMapping(value="/getCinemas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CinemaTheater>> getCinemas() {

		List<CinemaTheater> cinemas = cinemaTheaterService.findByType(enumProjection.valueOf("CINEMA"));
		return new ResponseEntity<List<CinemaTheater>>(cinemas, HttpStatus.OK);
	}
	
	@RequestMapping(value="/registerCinema", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<CinemaTheater> registerCinema(@RequestBody CinemaTheater cinema) {
		System.out.println("usaooooo");
		cinema.setType(enumProjection.CINEMA);
		CinemaTheater newCinema = cinemaTheaterService.save(cinema);
		return new ResponseEntity<>(newCinema, HttpStatus.OK);
	}
}
