package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.Friendship;
import isa.project.domain.User;
import isa.project.repositories.FriendshipRepository;
import isa.project.service.FriendshipService;

@Transactional
@Service
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	@Override
	public Friendship findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Friendship createNewFriendship(Friendship friendship) {
		// TODO Auto-generated method stub
		return friendshipRepository.save(friendship);
	}

	@Override
	public List<Friendship> findAll() {
		// TODO Auto-generated method stub
		return friendshipRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		 friendshipRepository.delete(id);
	}

	@Override
	public Friendship update(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Friendship> findBySender(User user) {
		// TODO Auto-generated method stub
		
		return friendshipRepository.findBysender(user);
	}
	
	
}
