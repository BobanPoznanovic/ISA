package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isa.project.domain.Hall;
import isa.project.domain.Projection;
import isa.project.domain.Term;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
	@SuppressWarnings("unchecked")
	public Term save(Term term);
	
//	@Query("SELECT t FROM Term t WHERE t.projection = :projection")
//	public List<Term> findAll(Projection projection);

	@Modifying(clearAutomatically = true)
    @Query(value = "update Term t set t.seats = :seatMap WHERE t.id = :id")
    public void update(@Param("id") Long id, @Param("seatMap") byte[] seatMap);
	
	@Query("SELECT DISTINCT t.termDate FROM Term t WHERE t.projection = :projection")
	public List<String> findDatesByProjection(@Param("projection") Projection projection);
	
	@Query("SELECT DISTINCT t FROM Term t WHERE t.projection = :projection and t.termDate = :termDate")
	public List<Term> findTermByProjectionDate(@Param("projection") Projection projection, @Param("termDate") String termDate);
	
	@Query("SELECT DISTINCT t.termTime FROM Term t WHERE t.projection = :projection and t.termDate = :termDate")
	public List<String> findTimesByProjectionDate(@Param("projection") Projection projection, @Param("termDate") String termDate);
	
	@Query("SELECT DISTINCT t.hall FROM Term t WHERE t.projection = :projection and t.termDate = :termDate and t.termTime = :termTime")
	public List<Hall> findHallByProjDateTime(@Param("projection") Projection projection, @Param("termDate") String termDate, @Param("termTime") String termTime);
	
	@Query("SELECT DISTINCT t.seats FROM Term t WHERE t.projection = :projection and t.termDate = :termDate and t.termTime = :termTime and t.hall = :hall")
	public byte[] findSeats(@Param("projection") Projection projection, @Param("termDate") String termDate, @Param("termTime") String termTime, @Param("hall") Hall hall);

	@Query("SELECT t FROM Term t WHERE t.projection = :projection and t.termDate = :termDate and t.termTime = :termTime and t.hall = :hall")
	public Term findTerm(@Param("projection") Projection projection, @Param("termDate") String termDate, @Param("termTime") String termTime, @Param("hall") Hall hall);
}
