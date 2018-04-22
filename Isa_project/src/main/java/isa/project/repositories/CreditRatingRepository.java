package isa.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.CreditRrating;

@Repository
public interface CreditRatingRepository extends JpaRepository<CreditRrating, Long>{ 

	
}
