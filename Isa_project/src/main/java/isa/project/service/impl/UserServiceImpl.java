package isa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.User;
import isa.project.repositories.UserRepository;
import isa.project.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
//    private final VerificationTokenRepository verificationTokenRepository;
//    private final Integer verificationTokenExpiryTime = 1440;

//    private final PasswordEncoder passwordEncoder;

    // USER RELATED (for all user subclasses)

   /* @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           WorkScheduleRepository workScheduleRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }*/

	
	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User createNewUser(User user) {
		// TODO Auto-generated method stub
		return  userRepository.save(user);
	}

	@Override
	public User delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(Long id) {
		// TODO Auto-generated method stub
	/*	User updated = new User();
		for(User user : userRepository.findAll())
		{
			if(user.getId().equals(id))
				updated = user;
				
		}*/
		return null;
//		userRepository.update(id);
	}
	
	@Override
	public User login(User user) {
		String email = user.getEmail();
		return this.userRepository.findByEmail(email);
	}
}
