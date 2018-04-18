package isa.project.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isa.project.domain.Projection;
import isa.project.domain.Term;
import isa.project.domain.User;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
	@SuppressWarnings("unchecked")
	public Term save(Term term);
	
	@Query("SELECT t FROM Term t WHERE t.projection = :projection")
	public List<Term> findAll(Projection projection);

	@Modifying(clearAutomatically = true)
    @Query(value = "update Term t set t.seats = :seatMap WHERE t.id = :id")
    public void update(@Param("id") Long id, @Param("seatMap") byte[] seatMap);
	
	public Term findOne(Long id);
}
