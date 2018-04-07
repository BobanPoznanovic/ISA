package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@SuppressWarnings("unchecked")
	public User save(User user);
	
	public List<User> findAll();

//	public User update(Long id);
}
