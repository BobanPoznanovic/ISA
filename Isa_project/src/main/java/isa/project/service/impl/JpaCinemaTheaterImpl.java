package isa.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isa.project.domain.CinemaTheater;
import isa.project.domain.enumProjection;
import isa.project.repositories.CinemaTheaterRepository;
import isa.project.service.CinemaTheaterService;


@Transactional
@Service
public class JpaCinemaTheaterImpl implements CinemaTheaterService{
	
	@Autowired
	private CinemaTheaterRepository cinemaTheaterRepository;
	
	@Override
	public List<CinemaTheater> findByType(enumProjection type) {
		// TODO Auto-generated method stub
			return cinemaTheaterRepository.findByType(type);
	}

	@Override
	public CinemaTheater findOne(Long id) {
		return cinemaTheaterRepository.findOne(id);
	}
	
	@Override
	public List<CinemaTheater> findAll() {
		return cinemaTheaterRepository.findAll();
	}

	@Override
	public CinemaTheater save(CinemaTheater cinemaTheater) {
		return cinemaTheaterRepository.save(cinemaTheater);
	}

	@Override
	public List<CinemaTheater> save(List<CinemaTheater> cinemaTheater) {
		return cinemaTheaterRepository.save(cinemaTheater);
	}

	@Override
	public CinemaTheater delete(Long id) {
		CinemaTheater cinemaTheater = cinemaTheaterRepository.findOne(id);
		if(cinemaTheater == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant country");
		}
		cinemaTheaterRepository.delete(cinemaTheater);
		return cinemaTheater;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}

	@Override
	public List<CinemaTheater> searchCinemas(String name, String address/*, String city*/) {
		// TODO Auto-generated method stub
		List<CinemaTheater> all = this.findByType(enumProjection.CINEMA);
		List<CinemaTheater> cinemas = new ArrayList<>();
		
		for(CinemaTheater ct : all)
		{
			if(ct.getName().toLowerCase().startsWith(name.toLowerCase()) && ct.getAdress().toLowerCase().startsWith(address.toLowerCase()) /*&& ct.getCity().toLowerCase().startsWith(city.toLowerCase())*/)
				cinemas.add(ct);
			else if(ct.getAdress().toLowerCase().startsWith(address.toLowerCase()) && name.equals(" ") /*&& city.equals(" ")*/)
				cinemas.add(ct);
//			else if(name.equals(" ") && address.equals(" ") && city.equals(" "))
//				cinemas.add(ct);
			else if(ct.getName().toLowerCase().startsWith(name.toLowerCase()) && address.equals(" ") /*&& city.equals(" ")*/)
				cinemas.add(ct);
//			else if(ct.getCity().toLowerCase().startsWith(city.toLowerCase()) && address.equals(" ") && name.equals(" "))
//				cinemas.add(ct);
		}
		for(CinemaTheater c : cinemas)
			System.out.println(c.getName());
		return cinemas;
	}

	@Override
	public List<CinemaTheater> searchTheaters(String name, String address/*, String city*/) {
		// TODO Auto-generated method stub
		List<CinemaTheater> all = this.findByType(enumProjection.THEATER);
		List<CinemaTheater> theaters = new ArrayList<>();
		
		for(CinemaTheater ct : all)
		{
			if(ct.getName().toLowerCase().startsWith(name.toLowerCase()) && ct.getAdress().toLowerCase().startsWith(address.toLowerCase()) /*&& ct.getCity().toLowerCase().startsWith(city.toLowerCase())*/)
				theaters.add(ct);
			else if(ct.getAdress().toLowerCase().startsWith(address.toLowerCase()) && name.equals(" ") /*&& city.equals(" ")*/)
				theaters.add(ct);
//			else if(name.equals(" ") && address.equals(" ") && city.equals(" "))
//				theaters.add(ct);
			else if(ct.getName().toLowerCase().startsWith(name.toLowerCase()) && address.equals(" ") /*&& city.equals(" ")*/)
				theaters.add(ct);
//			else if(ct.getCity().toLowerCase().startsWith(city.toLowerCase()) && address.equals(" ") && name.equals(" "))
//				cinemas.add(ct);
		}

		return theaters;
	}

	
	
}
