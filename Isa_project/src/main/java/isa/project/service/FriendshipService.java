package isa.project.service;

import java.util.List;

import isa.project.domain.Friendship;
import isa.project.domain.User;

public interface FriendshipService {
	
	Friendship findOne(Long id);
	
	Friendship createNewFriendship(Friendship friendship);
	
	List<Friendship> findAll();
	
	void delete(Long id);
	
	Friendship update(Long id);
	
	List<Friendship> findBySender(User user);
	
//	Friendship findOneBySender(User user);
}
