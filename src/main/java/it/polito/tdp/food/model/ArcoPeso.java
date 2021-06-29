package it.polito.tdp.food.model;

public class ArcoPeso {
	
	private Portion p;
	private int peso;
	
	public ArcoPeso(Portion p, int peso) {
		this.p = p;
		this.peso = peso;
	}

	public Portion getP() {
		return p;
	}

	public void setP(Portion p) {
		this.p = p;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public String toString() {
		return this.p.getPortion_display_name()+" - "+ this.peso;
	}
	
	

}
