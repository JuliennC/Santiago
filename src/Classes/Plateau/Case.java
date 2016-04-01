package Classes.Plateau;
import Classes.Tuile.Tuile;

public class Case {
	
	private int coordX;
	private int coordY;
	private boolean irriguee;
	private Tuile contientTuile;

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
	
	
	
}
