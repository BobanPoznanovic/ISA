package isa.project.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import isa.project.domain.Friendship;
import isa.project.domain.FriendshipStatus;
import isa.project.domain.User;
import isa.project.service.FriendshipService;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {

	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value = "/sendRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Friendship> sendRequest(@RequestBody User reciever)
	{
		User sender = (User)httpSession.getAttribute("user");
		Friendship friendship = new Friendship();

		friendship.setReciever(reciever);
		friendship.setSender(sender);
		friendship.setStatus(FriendshipStatus.WAITING);
		friendshipService.createNewFriendship(friendship);
		
		return new ResponseEntity<>(friendship, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFriendshipsStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Friendship>> searchFriendship()
	{ 
//		List<Friendship> friendships = friendshipService.findBySender(loggedIn);
		List<Friendship> friendships = friendshipService.findAll();
		return new ResponseEntity<>(friendships, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFriendRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> friendRequests()
	{
		User loggedIn = (User)httpSession.getAttribute("user");
		List<Friendship> friendRequests = friendshipService.findAll();
		List<User> myRequests = new ArrayList<>();
		
		for(Friendship fr : friendRequests)
		{
			if(fr.getReciever().getId().equals(loggedIn.getId()) && fr.getStatus().equals(FriendshipStatus.WAITING))
				myRequests.add(fr.getSender());
		}
		
		for(User f: myRequests)
			System.out.println("rrrrr " + f.getName());
		return new ResponseEntity<>(myRequests, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmRequest/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Friendship> friendRequests(@PathVariable Long id, @RequestBody User user)
	{
		User reciever = (User)httpSession.getAttribute("user");
		List<Friendship> friendRequests = friendshipService.findBySender(user);
		Friendship confirmed = new Friendship();
		
		for(Friendship fr : friendRequests)
		{
			if(fr.getSender().getId().equals(id) && fr.getReciever().getId().equals(reciever.getId()) && fr.getStatus().equals(FriendshipStatus.WAITING))
			{
				fr.setStatus(FriendshipStatus.ACCEPTED);
				confirmed = fr;
				friendshipService.update(fr.getId());
			}
		}
		
		return new ResponseEntity<Friendship>(confirmed, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFriendsList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> friendsList()
	{
		User loggedIn = (User)httpSession.getAttribute("user");
		List<Friendship> friendships = friendshipService.findAll();
		List<User> friends = new ArrayList<User>();
		
		for(Friendship fr : friendships)
		{
			if(fr.getStatus().equals(FriendshipStatus.ACCEPTED) && (fr.getReciever().getId().equals(loggedIn.getId()) || fr.getSender().getId().equals(loggedIn.getId())))
			{
				if(!fr.getReciever().getId().equals(loggedIn.getId()))
					friends.add(fr.getReciever());
				else if(!fr.getSender().getId().equals(loggedIn.getId()))
					friends.add(fr.getSender());
			}
		}
		
//		for(int i = 0; i < friends.size(); i++)
//			System.out.println("ffff " + friends.get(i).getName());
		
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/deleteFriendship/{senderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteFriendship(@PathVariable Long senderId)
	{
		User loggedIn = (User)httpSession.getAttribute("user");
		
		List<Friendship> friendships = friendshipService.findAll();

		for(Friendship fr : friendships)
		{
			if(fr.getStatus().equals(FriendshipStatus.ACCEPTED) || fr.getStatus().equals(FriendshipStatus.WAITING))
			{
				if(fr.getReciever().getId().equals(loggedIn.getId()) || fr.getSender().getId().equals(loggedIn.getId()))
					if(fr.getReciever().getId().equals(senderId) || fr.getSender().getId().equals(senderId))
						friendshipService.delete(fr.getId());
			}
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
