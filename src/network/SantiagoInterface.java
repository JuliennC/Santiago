package network;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import Classes.Joueur;
import Classes.Partie;
import Classes.Plateau.Canal;
import Classes.Plateau.Plateau;
import Classes.Tuile.Tuile;
import Exception.JoueurException;
import Exception.PartieException;
import main.MainClient;


public interface SantiagoInterface extends Remote{
	
	public String getName() throws RemoteException;
	
	
	public void send(String msg) throws RemoteException;
	public void addPseudo(String pseudo) throws RemoteException;;
	public Partie creerPartie(String nom, int nbJoueur) throws RemoteException, InterruptedException ;
	public void analyseConnexionJoueurs(final String nom) throws RemoteException;
	public Partie creerPartie(Partie aPartie) throws RemoteException;
	public Partie rejoindrePartie(String nom, SantiagoInterface i) throws RemoteException, PartieException, JoueurException;
	public Partie getPartieByName(String name) throws RemoteException;
	public Partie initialiserPartie(Partie aPartie) throws RemoteException ;
	public void enregistrePseudo(String pseudo) throws RemoteException;
	public boolean pseudoEstDisponible(String pseudo) throws RemoteException;
	public void testPartieEstPrete() throws PartieException, RemoteException, JoueurException;
	public boolean listeContientPartie(ArrayList<Partie> liste, String nomPartie) throws RemoteException;
	public void ajouterPartieListe(Partie p) throws RemoteException;
	public ArrayList<Partie> voirParties() throws RemoteException;
	public void sauvegarder(Partie p) throws RemoteException, FileNotFoundException, IOException;
	public Partie charger(String fileName) throws RemoteException, FileNotFoundException, IOException;
	public boolean reprendrePartie(Partie partieRejointe) throws RemoteException;

	public void setJoueur(Joueur j) throws RemoteException;

	public Joueur getJoueur() throws RemoteException;
	
	public int joueurFaitUneOffre() throws RemoteException;
	
	public int joueurChoisitTuile(int nbTuile) throws RemoteException;
	
	public int[] joueurChoisitPlacement() throws RemoteException;

	public void afficheErreur(String message) throws RemoteException;
	//public void addNotificationAPArtie(String nomPartie, int modif) throws RemoteException;
	public void poseTuileAvecXY(String nomPartie, Tuile tuile, int x, int y) throws RemoteException;

	public int propositionPhase4() throws RemoteException;

		public int joueurFaitPotDeVin() throws RemoteException;
		public void afficherPropositionsPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException;
		public SantiagoInterface soutenirJoueur(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException;
		public void cumulerPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, SantiagoInterface joueurSoutenu, int potDeVin) throws RemoteException;
		public SantiagoInterface choisirPotDeVin(Plateau plateau, HashMap<SantiagoInterface, Integer> listePropositions, ArrayList<Canal> listeCanauxTemp) throws RemoteException;
		public void deduirePotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens, HashMap<SantiagoInterface, Integer> listePotDeVin, SantiagoInterface constructeur) throws RemoteException;	
		public Canal poserCanalTemporaire(Plateau plateau, ArrayList<Canal> listeCanauxTemp) throws RemoteException;	
		

	public boolean tic() throws RemoteException;
}
