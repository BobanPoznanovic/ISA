package isa.project.domain;

import java.util.ArrayList;

public class Projection {
	private String name;
	private ArrayList<String> actors = new ArrayList<String>();
	private String genre;
	private String director;
	private String duration;
	private String poster;
	private float rating;
	private String description;
	//u kojim salama se projektuje
	private ArrayList<Hall> halls = new ArrayList<Hall>();
	
	private float price;
}
