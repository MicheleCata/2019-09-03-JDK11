package it.polito.tdp.food.model;

public class Adiacenze {
	
	private Portion p1;
	private Portion p2;
	private int peso;
	
	public Adiacenze(Portion p1, Portion p2, int peso) {
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}

	public Portion getP1() {
		return p1;
	}

	public void setP1(Portion p1) {
		this.p1 = p1;
	}

	public Portion getP2() {
		return p2;
	}

	public void setP2(Portion p2) {
		this.p2 = p2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	

}
