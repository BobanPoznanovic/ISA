package isa.project.controller;

import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.Ad;
import isa.project.domain.CinemaTheater;
import isa.project.domain.CreditRrating;
import isa.project.domain.Offer;
import isa.project.domain.Role;
import isa.project.domain.Status;
import isa.project.domain.User;
import isa.project.domain.enumProps;
import isa.project.service.CreditRatingService;
import isa.project.service.FunZoneService;
import isa.project.service.OfferService;
import isa.project.service.UserService;
import isa.project.utils.PasswordStorage;
import isa.project.utils.SendEmail;
import isa.project.utils.PasswordStorage.CannotPerformOperationException;;

@RestController
@RequestMapping(value = "funZone")
public class FunZoneController {
	
	@Autowired
	private FunZoneService funZoneService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreditRatingService creditRatingService;
	
	@Autowired
	private HttpSession httpSession;	
	
	@Transactional
	@RequestMapping(value="/createAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Ad> createAd(@RequestBody Ad ad) throws IOException {
		User user = (User)httpSession.getAttribute("user");
	
		ad.setApproved(false);
		ad.setCreator(user);
		user.getAds().add(ad);
		Ad newAd = funZoneService.save(ad);
		return new ResponseEntity<>(newAd, HttpStatus.OK);
	}
	
	@RequestMapping(value="/createOfficialAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Ad> createOfficialAd(@RequestBody Ad ad) {
		User user = (User)httpSession.getAttribute("user");
		if (user.getUserRole().equals(Role.ADMIN_OF_FAN_ZONE)) {
			ad.setCreator(user);
			ad.setApproved(null);
			user.getAds().add(ad);
			Ad newAd = funZoneService.save(ad);
			return new ResponseEntity<>(newAd, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getUsedApprovedAds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ad>> getUsedApprovedAds() {
		List<Ad> ad = funZoneService.findByApproved(true);
		return new ResponseEntity<List<Ad>>(ad, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAdsForApproved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ad>> getAdsForApproved() {
		List<Ad> ad = funZoneService.findByApproved(false);
		return new ResponseEntity<List<Ad>>(ad, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getOfficialAds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ad>> getOfficialAds() {
		List<Ad> ad = funZoneService.findByType(enumProps.valueOf("OFFICIAL"));
		return new ResponseEntity<List<Ad>>(ad, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getCreditRating", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditRrating> getCreditRating() {
		CreditRrating ad = creditRatingService.findOne(1L);
		return new ResponseEntity<CreditRrating>(ad, HttpStatus.OK);
	}
	
	@RequestMapping(value="/approvedAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ad> approvedAd(@RequestBody Ad ad) {
		
		Ad approvedAd = funZoneService.findOne(ad.getId());
		approvedAd.setApproved(true);
		funZoneService.save(approvedAd);
		
		return new ResponseEntity<Ad>(approvedAd, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectedAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ad> rejectedAd(@RequestBody Ad ad) {
		System.out.println("Oglas ID:"+ad.getId());
		Ad deleted = funZoneService.delete(ad.getId());
		
		return new ResponseEntity<Ad>(deleted, HttpStatus.OK);
	}
	
	@RequestMapping(value="/buyProp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ad> buyProp(@RequestBody Ad ad) {
		
		User user = (User)httpSession.getAttribute("user");
		user.setRating(user.getRating() + creditRatingService.findOne(1L).getForBuyOfficialProp());
//		userService.createNewUser(user);
		System.out.println(user.getRating());
		Ad deleted = funZoneService.delete(ad.getId());
		
		return new ResponseEntity<Ad>(deleted, HttpStatus.OK);
	}
	
	@RequestMapping(value="/changeAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ad> changeAd(@RequestBody Ad ad) {
		
		User user = (User)httpSession.getAttribute("user");
		if(user.getUserRole().equals(Role.ADMIN_OF_FAN_ZONE)){
		ad.setCreator(user);
		Ad save = funZoneService.save(ad);
		
		return new ResponseEntity<Ad>(save, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/changeRatings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditRrating> changeRatings(@RequestBody CreditRrating creditRating) {
		
		CreditRrating save = creditRatingService.save(creditRating);
		
		return new ResponseEntity<CreditRrating>(save, HttpStatus.OK);

	}
	
	@RequestMapping(value="/changeOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Offer> changeOffer(@RequestBody Offer offer) {
		
		User user = (User)httpSession.getAttribute("user");
		if(user.getId().equals(offer.getUser().getId())){
			Offer newOffer = offerService.save(offer);
		
		return new ResponseEntity<Offer>(newOffer, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/removeOfficialAd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ad> removeOfficialAd(@RequestBody Ad ad) {
		
		User user = (User)httpSession.getAttribute("user");
		if(user.getUserRole().equals(Role.ADMIN_OF_FAN_ZONE)){
			Ad save = funZoneService.delete(ad.getId());	
			return new ResponseEntity<Ad>(save, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/createOffer/{id}/{offer}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Offer> createOffer(@PathVariable Long id, @PathVariable float offer) {
		
		User user = (User)httpSession.getAttribute("user");
		Ad ad = funZoneService.findOne(id);
		for(int i = 0; i < ad.getOffers().size(); i++){
			if(user.getId().equals(ad.getOffers().get(i).getUser().getId())){
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		}
		
		Offer newOffer = new Offer(offer);
		newOffer.setUser(user);
		newOffer.setAd(ad);
		ad.getOffers().add(newOffer);
		
		offerService.save(newOffer);
		funZoneService.save(ad);
		
		return new ResponseEntity<>(newOffer, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getOffersOfAd/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<Offer>> getOffersOfAd(@PathVariable Long id) {
		
		Ad ad = funZoneService.findOne(id);
		
		return new ResponseEntity<>(ad.getOffers(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptOffer/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Offer> acceptOffer(@PathVariable Long id) {
		
		User user = (User)httpSession.getAttribute("user");
		Offer offer = offerService.findOne(id);

		System.out.println("usaoooooooooo");
		System.out.println(offer.getAd().getCreator().getId());
		if(!user.getId().equals(offer.getAd().getCreator().getId())){
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else{
			for(int i = 0; i < offer.getAd().getOffers().size(); i++){
				System.out.println(offer.getId());
				System.out.println(offer.getAd().getOffers().get(i).getId());
				if(offer.getId().equals(offer.getAd().getOffers().get(i).getId())){
					SendEmail.sendAcceptEmail(offer.getAd().getOffers().get(i).getUser().getEmail());
				}else{
					SendEmail.sendDeclineEmail(offer.getAd().getOffers().get(i).getUser().getEmail());
				}
			}

			offer.getUser().setRating(offer.getUser().getRating() + creditRatingService.findOne(1L).getForBuyUsedProp());
			userService.createNewUser(offer.getUser());
			
			List<Offer> deleteOffers = offerService.findByAdId(offer.getAd().getId());
			for(int i = 0; i < deleteOffers.size(); i++){
				System.out.println(deleteOffers.get(i).getId());
				offerService.delete(deleteOffers.get(i).getId());
			}
			
			Ad ad = funZoneService.delete(offer.getAd().getId());
			Offer newOffer = new Offer();
			return new ResponseEntity<>(newOffer, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/registerFunZoneAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerFunZoneAdmin(@RequestBody User user){
		
		
		user.setStatus(Status.ACTIVATED);
		user.setSalt(PasswordStorage.createSalt());
		user.setFirstLogIn(false);
		
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
	
	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerUser(@RequestBody User user){
		System.out.println("Enter register fun zone Admin");
		user.setUserRole(Role.ADMIN_OF_FAN_ZONE);
		user.setStatus(Status.ACTIVATED);
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
		//SendEmail.sendEmail(user.getEmail());
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/registerSystemAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerSystemUser(@RequestBody User user){
		System.out.println("Enter register System Admin");
		user.setUserRole(Role.ADMIN);
		user.setStatus(Status.ACTIVATED);
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
		//SendEmail.sendEmail(user.getEmail());
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePassword(@RequestBody User user){
		
		User logIn = (User)httpSession.getAttribute("user");
		User update = userService.findOne(logIn.getId());
		System.out.println("usaooooo");
		String hashedPassword;
		try {
			hashedPassword = PasswordStorage.createHash(update.getSalt(), user.getPassword());
			update.setPassword(hashedPassword);
		} catch (CannotPerformOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		update.setFirstLogIn(true);
		userService.update(update.getId());
		httpSession.setAttribute("user", update);
		
		return new ResponseEntity<>(update, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/acceptOfferTime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Offer>> acceptOfferTime(){
		Date date = new Date();
		date.getTime();
		
		System.out.println(date);
		List<Ad> ads = funZoneService.findByApproved(true);
		ArrayList<Offer> accepted = new ArrayList<Offer>();
		
		for(int i = 0; i < ads.size(); i++){
			Offer valueOfOffer = new Offer();
			valueOfOffer.setOffer(0);
			System.out.println(i);
			System.out.println(ads.get(i).getValid_to());
			if (date.after(ads.get(i).getValid_to())) {
				System.out.println("radiiiiiiii");
				for (int j = 0; j < ads.get(i).getOffers().size(); j++) {
					if (valueOfOffer.getOffer() < ads.get(i).getOffers().get(j).getOffer()) {
						valueOfOffer = ads.get(i).getOffers().get(j);						
					}
				}
				if(ads.get(i).getOffers().size() != 0){
					valueOfOffer.getUser().setRating(valueOfOffer.getUser().getRating() + creditRatingService.findOne(1L).getForBuyUsedProp());
					userService.createNewUser(valueOfOffer.getUser());
					accepted.add(valueOfOffer);
				}else{
					funZoneService.delete(ads.get(i).getId());
				}
			}
		}
		
		
		for(int i = 0; i < accepted.size(); i++){
			
			for(int j = 0; j < accepted.get(i).getAd().getOffers().size(); j++){
				offerService.delete(accepted.get(i).getAd().getOffers().get(j).getId());
			}
			
			funZoneService.delete(accepted.get(i).getAd().getId());
		}
		
		return new ResponseEntity<ArrayList<Offer>>(accepted, HttpStatus.OK);
		
	}
}
