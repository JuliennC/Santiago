package Classes.Tuile;

import java.rmi.server.UnicastRemoteObject;

public class TuileBanane extends Tuile  {

	public TuileBanane() {}
	
	public TuileBanane(int nbMarqueurs){
		
		super(nbMarqueurs);

		intituleDuChamps = "Champs de banane";

	}
	
}
