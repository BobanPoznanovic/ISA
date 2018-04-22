package isa.project;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.CinemaTheater;
import isa.project.domain.Hall;
import isa.project.domain.SeatMap;
import isa.project.domain.SeatType;
import isa.project.domain.User;
import isa.project.service.CinemaTheaterService;
import isa.project.service.HallService;
import isa.project.service.ProjectionService;
import isa.project.service.TermService;
import isa.project.service.UserService;

public class DataCreator {
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private CinemaTheaterService cinemaTheaterService;
	
	@Autowired
	private UserService userService;
	
	public DataCreator() {
		SeatMap seatMap = new SeatMap(5, 5);
		boolean[][] seats = new boolean[5][5];
		SeatType[][] seatTypes = new SeatType[5][5];
		
		for(int i=0;i<5;i++) 
		{
			for(int j=0;j<5;j++) 
			{
				seats[i][j]=true;
				if(i<1) 
				{
					seatTypes[i][j]=SeatType.BALCONY;
				}
				else {
					seatTypes[i][j]=SeatType.REGULAR;
				}
			}
		}
		
		seatMap.setFreeSeats(seats);
		seatMap.setSeatTypes(seatTypes);
		byte[] byteMap = null;
		
		try {
			byteMap = SeatMap.convertToBytes(seatMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Hall hall = new Hall();
		hall.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall.setColumns(2);
		hall.setRows(2);
		hall.setName("A1");
		hall.setSeats(byteMap);
		hallService.createNewHall(hall);
		
		Hall hall1 = new Hall();
		hall.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall.setColumns(2);
		hall.setRows(2);
		hall.setName("A2");
		hall.setSeats(byteMap);
		hallService.createNewHall(hall1);
		
		Hall hall2 = new Hall();
		hall.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall.setColumns(2);
		hall.setRows(2);
		hall.setName("B1");
		hall.setSeats(byteMap);
		hallService.createNewHall(hall2);
		
		
		
		
		
		
		
		
		
	}
}
