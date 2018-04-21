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

import isa.project.domain.Ad;
import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.domain.enumProps;
import isa.project.service.FunZoneService;;

@RestController
@RequestMapping(value = "funZone")
public class FunZoneController {
	
	@Autowired
	private FunZoneService funZoneService;
	
	@RequestMapping(value="/createAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Ad> createAd(@RequestBody Ad ad) {
		System.out.println("usaooooo");
		ad.setApproved(false);
		Ad newAd = funZoneService.save(ad);
		return new ResponseEntity<>(newAd, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getUsedApprovedAds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ad>> getUsedApprovedAds() {
		List<Ad> ad = funZoneService.findByApproved(true);
		return new ResponseEntity<List<Ad>>(ad, HttpStatus.OK);
	}
}
