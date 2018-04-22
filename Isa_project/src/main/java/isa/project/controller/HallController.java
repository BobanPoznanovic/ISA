package isa.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.Hall;
import isa.project.service.HallService;

@RestController
@RequestMapping(value="/hall")
public class HallController {

	@Autowired
	private HallService hallService;
	
	@RequestMapping(value = "/getRowsColumns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<String>> getRowsColumns(@RequestParam Long id)
	{
		ArrayList<String> rowsColums = new ArrayList<>();
		Hall hall = hallService.findOne(id);
		System.out.println("hala " + hall.getName());
		
		rowsColums.add(Integer.toString(hall.getRows()));
		rowsColums.add(Integer.toString(hall.getColumns()));		
		
		return new ResponseEntity<ArrayList<String>>(rowsColums, HttpStatus.OK);
	}
	
}
