package network;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import Classes.Joueur;
import Classes.Partie;
import Exception.PartieException;
import main.MainClient;


public interface SantiagoInterface extends Remote{
	
	public String getName() throws RemoteException;
	
	public SantiagoInterface getClient() throws RemoteException;
	public void setClient(SantiagoInterface s)	throws RemoteException;
	
	public void send(String msg) throws RemoteException;
	
	public Partie creerPartie() throws RemoteException;
	public void ajouterPartieListe(Partie p) throws RemoteException;
	
	public ArrayList<Partie> voirParties() throws RemoteException;
	
	public String rejoindrePartie(String nom, SantiagoInterface i) throws RemoteException, PartieException;

	public void enregistrePseudo(String pseudo) throws RemoteException;

	public boolean pseudoEstDisponible(String pseudo) throws RemoteException;
	
	public Joueur getJoueur() throws RemoteException;
	
	public int joueurFaitUneOffre() throws RemoteException;
	
	public int joueurChoisitTuile(int nbTuile) throws RemoteException;

	public void afficheErreur(String message) throws RemoteException;

	public int propositionPhase4() throws RemoteException;
		
	public int joueurFaitPotDeVin() throws RemoteException;
		
	public void afficherPropositionsPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException;
		
	public SantiagoInterface soutenirJoueur(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException;
		
	public void cumulerPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, SantiagoInterface joueurSoutenu, int potDeVin) throws RemoteException;
		
	public SantiagoInterface choisirPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException;
		
	public void deduirePotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens, HashMap<SantiagoInterface, Integer> listePotDeVin, SantiagoInterface constructeur) throws RemoteException;

}
