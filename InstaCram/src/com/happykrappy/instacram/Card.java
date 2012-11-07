package com.happykrappy.instacram;

public class Card {
	private int deckId;
	private byte[] front;
	private byte[] back;
	
	Card(int deckId) {
		this.deckId = deckId;
	}
	
	int getDeckId() {
		return deckId;
	}
	
	byte[] getFront() {
		return front;
	}
	
	byte[] getBack() {
		return back;
	}
	
	void setFront(byte[] front) {
		this.front = front;
	}
	
	void setBack(byte[] back) {
		this.back = back;
	}
}
