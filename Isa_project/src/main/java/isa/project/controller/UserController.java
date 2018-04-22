package isa.project.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import isa.project.domain.CinemaTheater;
import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.Reservation;
import isa.project.domain.ReservationStatus;
import isa.project.domain.Role;
import isa.project.domain.SeatMap;
import isa.project.domain.SeatType;
import isa.project.domain.Status;
import isa.project.domain.Term;
import isa.project.domain.User;
import isa.project.service.CinemaTheaterService;
import isa.project.service.HallService;
import isa.project.service.ProjectionService;
import isa.project.service.ReservationService;
import isa.project.service.TermService;
import isa.project.service.UserService;
import isa.project.utils.PasswordStorage;
import isa.project.utils.PasswordStorage.CannotPerformOperationException;
import isa.project.utils.SendEmail;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;	

	@Autowired
	private CinemaTheaterService cinemaTheaterService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody User user){
		
		SeatMap seatMap = new SeatMap(5, 5);
		boolean[][] seats = new boolean[5][5];
		
		for(int i = 0; i < 5; i++) 
		{
			for(int j = 0; j < 5; j++) 
			{
				seats[i][j]=true;
			}
		}
		
		seatMap.setFreeSeats(seats);
		
		try {
			termService.update(new Long(1), SeatMap.convertToBytes(seatMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setUserRole(Role.USER);
		user.setStatus(Status.NOT_ACTIVATED);
		user.setSalt(PasswordStorage.createSalt());
		user.setRating(0);
		
		String hashedPassword;
		try {
			hashedPassword = PasswordStorage.createHash(user.getSalt(), user.getPassword());
			user.setPassword(hashedPassword);
		} catch (CannotPerformOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userService.createNewUser(user);
		SendEmail.sendEmail(user.getEmail());
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/registerCinemaTheaterAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerCinemaTheaterAdmin(@RequestBody User user, @RequestParam(value = "id") int id){
		
		
		List<CinemaTheater> cinemas = cinemaTheaterService.findAll();
		for(int i = 0; i < cinemas.size(); i++){
			if(cinemas.get(i).getId() == id){
				user.setCinemaTheater(cinemas.get(i));
				cinemas.get(i).getCinemaTheaterAdmin().add(user);
			}
		}
		user.setStatus(Status.NOT_ACTIVATED);
		user.setSalt(PasswordStorage.createSalt());
		
		String hashedPassword;
		try {
			hashedPassword = PasswordStorage.createHash(user.getSalt(), user.getPassword());
			user.setPassword(hashedPassword);
		} catch (CannotPerformOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userService.createNewUser(user);
		
		SendEmail.sendEmail(user.getEmail());
		
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/confirmRegistration/{email:.+}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> confirmRegistration(@PathVariable String email)
	{	
		Long id = 0L;
		
		for(User user : userService.findAll())
		{
			if(user.getEmail().equals(email))
			{
				id = user.getId();
				user.setStatus(Status.ACTIVATED);
				userService.update(id);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllEmails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<User>> getAllEmails()
	{
		ArrayList<User> allEmails = new ArrayList<>();
		
		for(User user : userService.findAll())
		{
			allEmails.add(user);
		}
		
		return new ResponseEntity<ArrayList<User>>(allEmails, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user) throws java.text.ParseException
	{ 
		List<User> users = userService.findAll();
		User logged = new User();
		
		for(User u : users)
		{
			
			if(user.getEmail().equals(u.getEmail()))
			{
				byte[] salt = u.getSalt();
				String pass = user.getPassword();		// hash vrednost
				String hashed = "";
				
				try {
					hashed = PasswordStorage.createHash(salt, pass);
				} catch (CannotPerformOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(u.getPassword().equals(hashed))
				{
					logged = u;
					httpSession.setAttribute("user", logged);
					List<Reservation> checkReservations = reservationService.findReservationByStatus(u, ReservationStatus.CONFIRMED);
					
					for(int i = 0; i < checkReservations.size(); i++) 
					{
						String stringDate = checkReservations.get(i).getTerm().getTermDate()+" "+checkReservations.get(i).getTerm().getTermTime();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						Date date = dateFormat.parse(stringDate);
						
						if(date.compareTo(new Date()) < 0) 
						{
							reservationService.update(checkReservations.get(i).getId(), ReservationStatus.WATCHED);
						}
					}
					ArrayList<Reservation> reservationsWaiting = reservationService.findWaitingReservations(ReservationStatus.WAITING);
					
					for (int i = 0; i < reservationsWaiting.size(); i++) 
					{
						if(DateUtils.addDays(reservationsWaiting.get(i).getTimeOfReservation(),1).compareTo(new Date()) < 0) 
						{
							try {
								deleteReservation(reservationsWaiting.get(i).getId());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					break;
				
				}else {
					logged = null;
					return new ResponseEntity<User>(logged, HttpStatus.NOT_FOUND);
				}
			}
		}
//		za inicijalno kreiranje halls i terms zbog seats
		
		//createData();
		
		return new ResponseEntity<User>(logged, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLoggedInUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getLoggedInUser()
	{
		User loggedIn = (User)httpSession.getAttribute("user");
		
		if(loggedIn == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<User>(loggedIn, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchForFriends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> searchForFriends(@RequestParam String name, @RequestParam String surname)
	{
		List<User> allUsers = userService.findAll();
		List<User> friends = new ArrayList<>();
		
		
		for(User u : allUsers)
		{
			if(u.getStatus().equals(Status.ACTIVATED))		// u sistemu se ne vide oni koji nisu potvrdili registraciju		
				if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && u.getSurname().toLowerCase().startsWith(surname.toLowerCase()))
					friends.add(u);
				else if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && surname.equals(" "))
					friends.add(u);
				else if(u.getSurname().toLowerCase().startsWith(surname.toLowerCase()) && name.equals(" "))
					friends.add(u);
				else if(name.equals(" ") && surname.equals(" "))
					friends.add(u);
		}		
		
		return new ResponseEntity<List<User>>(friends, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user)
	{
		Long ID = Long.valueOf(id);
		User updated = userService.findOne(ID);
		
		byte[] salt = updated.getSalt();
		String pass = user.getPassword();		// hash vrednost
		String hashed = "";
		
		if(!pass.equals(""))
		{	try {
				hashed = PasswordStorage.createHash(salt, pass);
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String hashedPassword = "";
			if(!updated.getPassword().equals(hashed))
			{
				updated.setSalt(PasswordStorage.createSalt());
				try {
					hashedPassword = PasswordStorage.createHash(updated.getSalt(), user.getPassword());
					updated.setPassword(hashedPassword);
				} catch (CannotPerformOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		updated.setName(user.getName());
		updated.setSurname(user.getSurname());
		updated.setCity(user.getCity());
		updated.setEmail(user.getEmail());
		updated.setStatus(user.getStatus());
		updated.setPhone(user.getPhone());
		userService.update(ID);
		httpSession.setAttribute("user", updated);
		
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout()
	{
//		 request.getSession().invalidate();
		httpSession.invalidate();
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getUsersNames(@RequestBody String friends)
	{
		return new ResponseEntity<List<String>>(HttpStatus.OK);
	}
	
	public void deleteReservation(Long id) throws ClassNotFoundException, IOException, ParseException
	{
		
		Reservation reservation = reservationService.findOne(id);
		SeatMap currSeatMap = (SeatMap) SeatMap.convertToSeatMap(reservation.getTerm().getSeats());
		currSeatMap.getFreeSeats()[reservation.getRow()][reservation.getColumn()]=true;
		termService.update(reservation.getTerm().getId(),SeatMap.convertToBytes(currSeatMap));	
		
		reservationService.delete(id);
		return;
	}
	
	@SuppressWarnings("deprecation")
	public void createData() {
		SeatMap seatMap = new SeatMap(5, 5);
		boolean[][] seats = new boolean[5][5];
		SeatType[][] seatTypes = new SeatType[5][5];
		
		for(int i = 0 ; i < 5; i++) 
		{
			for(int j = 0; j < 5; j++) 
			{
				seats[i][j]=true;
				if(i<2) {
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
		hall.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(458754)));
		hall.setColumns(5);
		hall.setRows(5);
		hall.setName("A1");
		hall.setSeats(byteMap);
		hallService.createNewHall(hall);
		
		Hall hall1 = new Hall();
		hall1.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(458754)));
		hall1.setColumns(5);
		hall1.setRows(5);
		hall1.setName("A2");
		hall1.setSeats(byteMap);
		hallService.createNewHall(hall1);
		
		Hall hall2 = new Hall();
		hall2.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(458754)));
		hall2.setColumns(5);
		hall2.setRows(5);
		hall2.setName("B1");
		hall2.setSeats(byteMap);
		hallService.createNewHall(hall2);
	
		Projection pr1 = new Projection();
		pr1.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(458754)));
		pr1.setActors("Actors");
		pr1.setDescription("Desc");
		pr1.setDirector("Dir");
		pr1.setDuration(new Date(1, 1, 2019, 1, 0));
		pr1.setGenre("Gen");
		pr1.setName("Film");
		projectionService.save(pr1);
		
		Projection pr2 = new Projection();
		pr2.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(458754)));
		pr2.setActors("Actors");
		pr2.setDescription("Desc");
		pr2.setDirector("Dir");
		pr2.setDuration(new Date(1, 1, 2019, 2, 0));
		pr2.setGenre("Gen");
		pr2.setName("Filmovi");
		projectionService.save(pr2);
		
		Term term1 = new Term();
		term1.setHall(hallService.findOne(Long.valueOf(1)));
		term1.setPriceBalcony(100);
		term1.setPriceRegular(50);
		term1.setPriceVip(150);
		term1.setProjection(projectionService.findOne(Long.valueOf(1)));
		term1.setTermDate("2018-11-08");
		term1.setTermTime("20:00");
		term1.setSeats(byteMap);
		termService.createNewTerm(term1);		
		
		Term term2 = new Term();
		term2.setHall(hallService.findOne(Long.valueOf(2)));
		term2.setPriceBalcony(100);
		term2.setPriceRegular(50);
		term2.setPriceVip(150);
		term2.setProjection(projectionService.findOne(Long.valueOf(1)));
		term2.setTermDate("2019-02-15");
		term2.setTermTime("13:00");
		term2.setSeats(byteMap);
		termService.createNewTerm(term2);
		
		Term term3 = new Term();
		term3.setHall(hallService.findOne(Long.valueOf(1)));
		term3.setPriceBalcony(100);
		term3.setPriceRegular(50);
		term3.setPriceVip(150);
		term3.setProjection(projectionService.findOne(Long.valueOf(2)));
		term3.setTermDate("2020-01-18");
		term3.setTermTime("17:00");
		term3.setSeats(byteMap);
		termService.createNewTerm(term3);
	}
	
}
