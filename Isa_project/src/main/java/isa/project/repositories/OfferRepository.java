package isa.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isa.project.domain.Ad;
import isa.project.domain.Offer;
import isa.project.domain.enumProps;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

	public List<Offer> findByAdId(Long id);
}