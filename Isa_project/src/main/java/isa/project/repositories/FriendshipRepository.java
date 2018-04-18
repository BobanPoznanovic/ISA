package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.Friendship;
import isa.project.domain.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long>{

	@SuppressWarnings("unchecked")
	public Friendship save(Friendship friendship);
	
	public List<Friendship> findAll();
	
	public Friendship findOne(Long id);
	
	public List<Friendship> findBysender(User id);
	
	public Friendship findOneBySender(Long id);
}
