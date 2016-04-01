package Classes.Plateau;

public class Case {
	
	private int coorX;
	private int coorY;
	private boolean irriguee;
	private Tuile contientTuile;

	public Case(int coorX, int coorY) {
		super();
		this.coorX = coorX;
		this.coorY = coorY;
		irriguee = false;
	}

	public boolean isIrriguee() {
		return irriguee;
	}
	public void setIrriguee(boolean irriguee) {
		this.irriguee = irriguee;
	}
	
	
}
