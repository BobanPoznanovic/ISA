package isa.project.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.SeatMap;
import isa.project.domain.Term;
import isa.project.service.HallService;
import isa.project.service.ProjectionService;
import isa.project.service.TermService;

@RestController
@RequestMapping(value = "/term")
public class TermController {
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private HallService hallService;
	
	@RequestMapping(value = "/getTerms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Term>> getTerms(@RequestParam Long projectionId, @RequestParam String termDate) throws ClassNotFoundException, IOException
	{
		
		System.out.println(projectionId);
		Projection projection = projectionService.findOne(projectionId);
		List<Term> terms = termService.findTermByProjectionDate(projection, termDate);
		
		for(Term t : terms)
			System.out.println("ttt " + t.getTermDate());
		
		return new ResponseEntity<>(terms, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getDates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getDates(@RequestParam Long id) throws ParseException
	{
		Projection projection = projectionService.findOne(id);
		List<String> dates = termService.findDatesByProjection(projection);
		List<String> retVal = new ArrayList<>();
		
		for(String d : dates)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date  = dateFormat.parse(d);
			
			if(date.compareTo(new Date()) < 0)
				continue;
			
			retVal.add(d);
		}
		
		return new ResponseEntity<List<String>>(retVal, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTimes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getTimes(@RequestParam Long projectionId, @RequestParam String termDate)
	{
		Projection projection = projectionService.findOne(projectionId);
		System.out.println(projection.getName() + projection.getId());
		List<String> times = termService.findTimesByProjectionDate(projection, termDate);
		
		for(String d : times)
			System.out.println("time " + d);
		
		return new ResponseEntity<List<String>>(times, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getHalls", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hall>> getHalls(@RequestParam Long projectionId, @RequestParam String termDate, @RequestParam String termTime)
	{
		Projection projection = projectionService.findOne(projectionId);
		List<Hall> halls = termService.findHallByProjDateTime(projection, termDate, termTime);
		List<String> retVal = new ArrayList<>();
		
		for(Hall h : halls)
		{	
			retVal.add(h.getName());
			System.out.println("halls " + h.getName());
		}
		
		return new ResponseEntity<List<Hall>>(halls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSeats", method = RequestMethod.GET)
	public ResponseEntity<SeatMap> getSeats(@RequestParam("projId") Long projId, @RequestParam("termDate") String termDate, @RequestParam("termTime") String termTime, @RequestParam("hallId") Long hallId)
	{
		System.out.println("usaoooo");
		Projection projection = projectionService.findOne(projId);
		Hall hall = hallService.findOne(hallId);
		System.out.println(projection.getName() + hall.getName());
		byte[] seats = termService.findSeats(projection, termDate, termTime, hall);
		System.out.println("gore" + seats);
		SeatMap seatMap = new SeatMap();
		try {
			seatMap = SeatMap.convertToSeatMap(seats);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ss " + seatMap);
		
		return new ResponseEntity<SeatMap>(seatMap, HttpStatus.OK);
	}
}
