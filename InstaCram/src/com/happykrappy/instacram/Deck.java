package com.happykrappy.instacram;

public class Deck {
	private String name;
	private int id;
	
	Deck() {
		
	}
	
	Deck(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
