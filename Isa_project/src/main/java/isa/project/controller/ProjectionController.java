package isa.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.Projection;
import isa.project.service.ProjectionService;

@RestController
@RequestMapping(value = "/projection")
public class ProjectionController {
	
	@Autowired
	private ProjectionService projectionService;
	
	@RequestMapping(value = "/getProjections", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Projection>> getProjections(@RequestParam Long id)
	{
		List<Projection> projections = projectionService.findAll();
		List<Projection> retVal = new ArrayList<>();
		
		for(Projection pr : projections)
		{
			if(pr.getCinemaTheater().getId().equals(id))
				retVal.add(pr);
			
		}
			
		for(Projection p: retVal)
			System.out.println(p.getName());
		
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Projection> getProjectionsName(@RequestParam Long id)
	{
		Projection projection = projectionService.findOne(id);
		String name = projection.getName();
		System.out.println(name);
		
		return new ResponseEntity<>(projection, HttpStatus.OK);
	}
}
