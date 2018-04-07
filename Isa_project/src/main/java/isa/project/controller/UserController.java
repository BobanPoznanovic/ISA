package isa.project.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.HibernateException;
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

import isa.project.domain.CinemaTheater;
import isa.project.domain.Role;
import isa.project.domain.Status;
import isa.project.domain.User;
import isa.project.service.CinemaTheaterService;
import isa.project.service.UserService;
import isa.project.utils.PasswordStorage;
import isa.project.utils.PasswordStorage.CannotPerformOperationException;
import isa.project.utils.PasswordStorage.InvalidHashException;
import isa.project.utils.SendEmail;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CinemaTheaterService cinemaTheaterService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody User user){
//		String reg = "";
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
		
//		try{
		userService.createNewUser(user);
//			reg = "Email sent, please confirm your registration.";
//		}catch(HibernateException hb){
//			reg = "Registration failed, please try again.";
//		}
		
		System.out.println("aaaaaaaaaaa created " + user.getName());
		
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
		System.out.println("usao");
		System.out.println("prolsedjen " + email);
		String confirmed = "";
		
		for(User user : userService.findAll())
		{
			System.out.println("u petlji " + user.getEmail());
			if(user.getEmail().equals(email))
			{
				id = user.getId();
				System.out.println(user.getName() + " " + user.getStatus());
				user.setStatus(Status.ACTIVATED);
				userService.update(id);
				confirmed = "confirmed";
				
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
		
		return new ResponseEntity<ArrayList<User>>(allEmails, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user)
	{
		List<User> users = userService.findAll();
		User logged = new User();
		for(User u : users)
		{
			System.out.println("mmmmaaa " + user.getEmail());
			System.out.println("uu " + u.getEmail());
			
			if(user.getEmail().equals(u.getEmail()))
			{
				System.out.println("salta " + u.getSalt().toString());
				System.out.println("usao u else");
				byte[] salt = u.getSalt();
				String pass = user.getPassword();		// hash vrednost
				System.out.println("salt " + salt);
				System.out.println("prosledjeno " + pass);
				System.out.println("iz baze " + u.getPassword());
				String hashed = "";
				
				
				try {
					hashed = PasswordStorage.createHash(salt, pass);
					System.out.println("hashovao " + hashed);
				} catch (CannotPerformOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(u.getPassword().equals(hashed))
				{
					logged = u;
					break;
				
				}else {
					logged = null;
					return new ResponseEntity<User>(logged, HttpStatus.FORBIDDEN);
				}
			}
		}
		System.out.println("dole je");
		System.out.println("ulogovan " + logged);
		return new ResponseEntity<User>(logged, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
}
