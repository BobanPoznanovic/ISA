package isa.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.dialect.PostgreSQL82Dialect;
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

import com.sun.mail.handlers.text_html;

import isa.project.DataCreator;
import isa.project.domain.CinemaTheater;
import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.Role;
import isa.project.domain.SeatMap;
import isa.project.domain.SeatType;
import isa.project.domain.Status;
import isa.project.domain.Term;
import isa.project.domain.User;
import isa.project.service.CinemaTheaterService;
import isa.project.service.HallService;
import isa.project.service.ProjectionService;
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
	private HallService hallService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private HttpSession httpSession;	

	@Autowired
	private CinemaTheaterService cinemaTheaterService;

	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody User user){
	
		createData();
		user.setUserRole(Role.USER);
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
	
	@RequestMapping(value = "/registerCinemaTheaterAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerCinemaTheaterAdmin(@RequestBody User user, @RequestParam(value = "id") int id){
		
		
		List<CinemaTheater> cinemas = cinemaTheaterService.findAll();
		for(int i = 0; i < cinemas.size(); i++){
			if(cinemas.get(i).getId() == id){
				user.setCinemaTheater(cinemas.get(i));
				cinemas.get(i).getCinemaTheaterAdmin().add(user);
			//	cinemaTheaterService.delete(cinemas.get(i).getId());
			//	CinemaTheater save = cinemaTheaterService.save(cinemas.get(i));
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
	
	@RequestMapping(value = "/getAllEmails", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllEmails()
	{
		ArrayList<User> allEmails = new ArrayList<>();
		
		for(User user : userService.findAll())
		{
			allEmails.add(user);
		}
		
//		 System.out.println("user ulogovan " + u.getEmail());
		return new ResponseEntity<ArrayList<User>>(allEmails, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user)
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
					break;
				
				}else {
					logged = null;
					return new ResponseEntity<User>(logged, HttpStatus.NOT_FOUND);
				}
			}
		}
		return new ResponseEntity<User>(logged, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLoggedInUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getLoggedInUser()
	{
		User loggedIn = (User)httpSession.getAttribute("user");
//		if(loggedIn != null)
//			System.out.println("uuuuu " + loggedIn.getSurname());
		if(loggedIn == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<User>(loggedIn, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchForFriends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> searchForFriends(@RequestParam String name, @RequestParam String surname)
	{
		List<User> allUsers = userService.findAll();
		List<User> friends = new ArrayList<>();
		
/*		for(User u : allUsers)
		{
			System.out.println("za usera " + u.getName());
			List<Friendship> friendship = friendshipService.findBySender(u);
			if(u.getStatus().equals(Status.ACTIVATED) && !friendship.isEmpty())
			{
				
					for(Friendship fr : friendship)
					{	
						System.out.println("fr " + fr.getId());
						if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && u.getSurname().toLowerCase().startsWith(surname.toLowerCase()))
						{	
							UserDTO udto = new UserDTO(fr.getReciever(), fr.getStatus());
							hs.add(udto);
							System.out.println("1 " + udto.getName());
						}else if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && surname.equals(" "))
						{	UserDTO udto = new UserDTO(fr.getReciever(), fr.getStatus());
							hs.add(udto);
							System.out.println("2 " + udto.getName());
						}
						else if(u.getSurname().toLowerCase().startsWith(surname.toLowerCase()) && name.equals(" "))
						{	UserDTO udto = new UserDTO(fr.getReciever(), fr.getStatus());
							hs.add(udto);
							System.out.println("3 " + udto.getName());
						}
						else if(name.equals(" ") && surname.equals(" "))
						{	UserDTO udto = new UserDTO(fr.getReciever(), fr.getStatus());
							System.out.println("dodao " + udto.getName());
							hs.add(udto);
						}
					}
				}else if(u.getStatus().equals(Status.ACTIVATED) && friendship.isEmpty())
				{
					System.out.println("dole za usera " + u.getName());
					if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && u.getSurname().toLowerCase().startsWith(surname.toLowerCase()))
					{
					UserDTO udto = new UserDTO(u, null);
					hs.add(udto);
					System.out.println("4 " + udto.getName());
					}else if(u.getName().toLowerCase().startsWith(name.toLowerCase()) && surname.equals(" "))
					{	UserDTO udto = new UserDTO(u, null);
						hs.add(udto);
						System.out.println("5 " + udto.getName());
					}
					else if(u.getSurname().toLowerCase().startsWith(surname.toLowerCase()) && name.equals(" "))
					{	UserDTO udto = new UserDTO(u, null);
						hs.add(udto);
						System.out.println("6 " + udto.getName());
					}
					else if(name.equals(" ") && surname.equals(" "))
					{	UserDTO udto = new UserDTO(u, null);
						hs.add(udto);
						System.out.println("doel dodao " + udto.getName());
					}
					}
			}*/
		
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
		System.out.println(ID + " " + user.getName());
		User updated = userService.findOne(ID);
		System.out.println("ovaj updatuje " + updated.getName());
		
		byte[] salt = updated.getSalt();
		String pass = user.getPassword();		// hash vrednost
		String hashed = "";
		System.out.println("up " + updated.getPassword());
		System.out.println("up " + user.getPassword());
		
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
		
		System.out.println("user " + updated.getName());
		
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout()
	{
//		 request.getSession().invalidate();
		httpSession.invalidate();
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	public void createData() {
		SeatMap seatMap = new SeatMap(5, 5);
		boolean[][] seats = new boolean[5][5];
		SeatType[][] seatTypes = new SeatType[5][5];
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
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
		hall.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall.setColumns(5);
		hall.setRows(5);
		hall.setName("A1");
		hall.setSeats(byteMap);
		hallService.createNewHall(hall);
		
		Hall hall1 = new Hall();
		hall1.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall1.setColumns(5);
		hall1.setRows(5);
		hall1.setName("A2");
		hall1.setSeats(byteMap);
		hallService.createNewHall(hall1);
		
		Hall hall2 = new Hall();
		hall2.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		hall2.setColumns(5);
		hall2.setRows(5);
		hall2.setName("B1");
		hall2.setSeats(byteMap);
		hallService.createNewHall(hall2);
	
		Projection pr1 = new Projection();
		pr1.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		pr1.setActors("Actors");
		pr1.setDescription("Desc");
		pr1.setDirector("Dir");
		pr1.setDuration(new Date(1, 1, 2019, 1, 0));
		pr1.setGenre("Gen");
		pr1.setName("Film");
		projectionService.createNewProjection(pr1);
		
		Projection pr2 = new Projection();
		pr2.setCinemaTheater(cinemaTheaterService.findOne(Long.valueOf(0)));
		pr2.setActors("Actors");
		pr2.setDescription("Desc");
		pr2.setDirector("Dir");
		pr2.setDuration(new Date(1, 1, 2019, 2, 0));
		pr2.setGenre("Gen");
		pr2.setName("Filmovi");
		projectionService.createNewProjection(pr2);
		
		Term term1 = new Term();
		term1.setHall(hallService.findOne(Long.valueOf(1)));
		term1.setPriceBalcony(100);
		term1.setPriceRegular(50);
		term1.setPriceVip(150);
		term1.setProjection(projectionService.findOne(Long.valueOf(1)));
		term1.setTermDate(new Date(14, 4, 2018));
		term1.setTermTime(new Date());
		termService.createNewTerm(term1);		
		
		Term term2 = new Term();
		term2.setHall(hallService.findOne(Long.valueOf(2)));
		term2.setPriceBalcony(100);
		term2.setPriceRegular(50);
		term2.setPriceVip(150);
		term2.setProjection(projectionService.findOne(Long.valueOf(1)));
		term2.setTermDate(new Date(15, 4, 2018));
		term2.setTermTime(new Date());
		termService.createNewTerm(term2);
		
		Term term3 = new Term();
		term3.setHall(hallService.findOne(Long.valueOf(1)));
		term3.setPriceBalcony(100);
		term3.setPriceRegular(50);
		term3.setPriceVip(150);
		term3.setProjection(projectionService.findOne(Long.valueOf(2)));
		term3.setTermDate(new Date(16, 4, 2018));
		term3.setTermTime(new Date());
		termService.createNewTerm(term3);
	}
}
