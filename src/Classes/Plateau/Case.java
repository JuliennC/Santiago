package Classes.Plateau;
import java.io.Serializable;

import Classes.Tuile.Tuile;

/**
 * Classe Case
 */

public class Case implements Serializable{
	
	private int coordX;
	private int coordY;
	private boolean irriguee;
	private Tuile contientTuile;
	
	public Case() {}

	public Case(int coordX, int coordY, boolean irriguee, Tuile contientTuile) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
		this.irriguee = irriguee;
		this.contientTuile = contientTuile;
	}

	public boolean isIrriguee() {
		return irriguee;
	}
	public void setIrriguee(boolean irriguee) {
		this.irriguee = irriguee;
	}
	public int getCoorX() {
		return coordX;
	}
	public void setCoorX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoorY() {
		return coordY;
	}
	public void setCoorY(int coordY) {
		this.coordY = coordY;
	}
	public Tuile getContientTuile() {
		return contientTuile;
	}
	public void setContientTuile(Tuile contientTuile) {
		this.contientTuile = contientTuile;
	}

	@Override
	public String toString() {
		return "Case [coordX=" + coordX + ", coordY=" + coordY + ", irriguee=" + irriguee + ", contientTuile="
				+ contientTuile + "]";
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	
	
	
}
