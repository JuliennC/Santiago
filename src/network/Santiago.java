package network;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Classes.Joueur;
import Classes.Partie;
import Exception.JoueurException;
import Exception.PartieException;
import main.MainClient;


public class Santiago extends UnicastRemoteObject implements SantiagoInterface {
	Scanner scString = new Scanner(System.in);
	Scanner scInt = new Scanner(System.in);
	
	private ArrayList<Partie> listeParties = new ArrayList<>();
	
	
	protected String name;
	protected Joueur joueur;
	private SantiagoInterface client = null;
	private static ArrayList<String>listePseudos = new ArrayList<>();

	

	
	
	public Santiago(Joueur j) throws RemoteException {
		super();
		joueur = j;
		this.name = j.getPseudo();
	}

	
	public Santiago(String name) throws RemoteException {
		super();

		this.name = name;
	}
	
	
	public Joueur getJoueur() throws RemoteException{
		return joueur;
	}
	
	
	
	
	
	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public SantiagoInterface getClient() throws RemoteException {
		// TODO Auto-generated method stub
		return client;
	}
	
	@Override
	public void setClient(SantiagoInterface s) throws RemoteException {
		client = s;

	}

	@Override
	public void send(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
	/* ------------------------------------------------------------------------------- */

	/**
	 * Fonction appelée par le client afin de créer une partie
	 * Retourne une Partie qui sera transmise sur le serveur par la suite
	 * @return: Partie p
	 */
	@Override
	public Partie creerPartie() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Insérez le nom de votre partie:");
		String nom = scString.nextLine();
		
		System.out.println("Insérez le nombre de joueur:");
		int nbJoueur = scInt.nextInt();
		
		Partie p = null;
		try {
			p = new Partie(nom, nbJoueur);

		} catch (PartieException e) {

			e.printStackTrace();
		}
		
		
		System.out.println("La partie a été créée");
		
		
		return p;
		
	}

	/**
	 * Fonction appelée par le client pour rejoindre une partie
	 * Le client renseigne le nom de la partie à rejoindre et son objet Joueur
	 * @return: booléen de confirmation
	 * @throws PartieException 
	 */
	@Override
	public String rejoindrePartie(String nom, SantiagoInterface i) throws RemoteException, PartieException {

		String res = null;

		/* On vérifie d'abord que la liste contient la partie demandée, 
		*  Sinon, on lève une exception
		*/
		if (! listeContientPartie(listeParties, nom)){
			res = ("Aucune partie contenant ce nom n'est enregistrée");
			return res;
		}
		
		
		for(Partie p:listeParties) {
			if(p.getNomPartie().equals(nom)) {
				try {
					
					/* On vérifie d'abord que la partie n'a pas déjà commencé, 
					*  Sinon, on lève une exception
					*/
					if(p.getPartieACommence()){

						res = ("Vous ne pouvez pas rejoindre la partie, elle a déjà commencé.");
						return res;
					} 
					
					p.addClient(i);
					
					//On test si la partie est complète
					testPartieEstPrete(p);
					
				} catch (PartieException e) {

					e.printStackTrace();
				}
				return res;
			}
		}
		
		return ("Pas de chance : Une erreur inconnue est survenue");
	}
	
	
	
	/**
	 * Foncion qui demande à l'utilisateur d'entrer une offre
	 * 
	 * @param : void
	 * @return : int --> l'offre
	 */
	public int joueurFaitUneOffre() throws RemoteException{		
		
		Scanner scInt = new Scanner(System.in);

		int offre = 0;

		boolean offreOk = false;
		
		while(! offreOk) {

			//On récupère l'offre
			System.out.println("["+joueur.getPseudo()+"] : Vous devez faire une enchère : ");
			String str = scInt.nextLine();
			offre = Integer.parseInt(str);
			
			//On regarde la conformité de l'offre
			if (offre < 0) {
				System.out.println("Vous ne pouvez pas faire une offre inférieur à 0");
		
			} else if (offre > joueur.getSolde()) {

				System.out.println("Vous ne pouvez pas faire une offre supérieur à votre solde : "+joueur.getSolde()+".");
			
			} else {
			
				offreOk = true;
			}
			
		}
		
		
		return offre;
	}

	
	/**
	 * Fonction qui demande un choix à l'utilisateur concernant les pots de vin
	 * @return int choix
	 */
	public int propositionPhase4() throws RemoteException {
		boolean choixValide = false;
		int choix = 0;
		
		while(!choixValide) {
			System.out.println("[1] Je souhaite proposer un pot de vin au constructeur de canal pour construire mon canal");
			System.out.println("[2] Je souhaite soutenir la proposition de pot de vin d'un joueur");
			System.out.println("[3] Je souhaite passer mon tour");		
			
			try {
				choix = scInt.nextInt();
				
				if(choix < 4 && choix > 0) {
					choixValide = true;
				}
		    } catch (InputMismatchException ex) {
		    	System.out.println("Erreur dans la valeur. \n");
		    } 		
		}
		return choix;
	}
	

	public int joueurChoisitTuile(int nbTuile){
		return this.joueur.joueurChoisitTuile(nbTuile);
	}
		
	/**
	 * Fonction appelée pour le choix 1
	 * Demande un pot de vin et vérifier si le pot de vin est possible
	 * @return: montant : Pot de vin
	 */
	public int joueurFaitPotDeVin() throws RemoteException {
		boolean montantValide = false;
		int montant = 0;
		
		while(!montantValide) {
			System.out.println("Saisissez le montant de votre pot de vin:");
			
			try {
				montant = scInt.nextInt();
				
				if(montant > 0 && joueur.getSolde()-montant >= 0) {
					montantValide = true;
				} else {
					System.out.println("Votre solde ne peut être inférieur à 0 ! Montant maximum accepté: " + joueur.getSolde() + " Escudos.");
				}
		    } catch (InputMismatchException ex) {
		    	System.out.println("Erreur dans la valeur. \n");
		    } 
		}
		
		return montant;
	}
	
	/**
	 * Afficher la liste des propositions de pot de vin (Avec cumul des pots de vin)
	 * 
	 */
	@Override
	public void afficherPropositionsPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException{
		for(SantiagoInterface si : listePropositions.keySet()) {
			System.out.println("Joueur : " +si.getJoueur().getPseudo() + " | Couleur : " + si.getJoueur().getCouleur() + 
					" | Montant proposé: " +listePropositions.get(si));
		}
	}
	
	/**
	 * Fonction appelée pour le choix 2
	 * Affiche la liste des propositions (Fonction afficherPropositionsPotDeVin)
	 * Demande de choisir un joueur dans la liste
	 * @return SantiagoInterface: joueur soutenu
	 */
	public SantiagoInterface soutenirJoueur(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException {
		boolean choixJoueur = false;
		String choix = "";
		SantiagoInterface joueur = null;
		
		System.out.println("La liste des pots de vin: ");
		afficherPropositionsPotDeVin(listePropositions);
		

		while(!choixJoueur) {
			if(choix.contains("")) {
				System.out.println("Quel joueur voulez vous soutenir ?");
			} else {
				System.out.println("Ce joueur n'est pas dans la liste des pots de vin.. Réessayez :");
				System.out.println("Quel joueur voulez vous soutenir ?");
			}
			
			choix = scString.nextLine();			
			
			for(SantiagoInterface si : listePropositions.keySet()){
				if(si.getJoueur().getPseudo().equals(choix) ) {
					choixJoueur = true;
					joueur = si;
					break;
				}
			}
			choix = "";
		}

		return joueur;
	}
	
	/**
	 * Fonction appelée à la suite du soutien d'un joueur
	 * Cette fonction à pour rôle de cumuler le pot de vin du joueur au pot de vin du joueur soutenu
	 * Actualiste la liste des propositions
	 */
	@Override
	public void cumulerPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, SantiagoInterface joueurSoutenu, int potDeVin) throws RemoteException {
		for(SantiagoInterface si : listePropositions.keySet()) {
			if(si.equals(joueurSoutenu)) {
				listePropositions.replace(si, listePropositions.get(si) + potDeVin);
				break;								
			}
		}
		
		System.out.println("Votre pot de vin a été cumulé au pot de vin de " + joueurSoutenu.getJoueur().getPseudo());
	}
	
	/**
	 * Fonction appelée par le constructeur de canal
	 * Cette fonction appelle la fonction(constructeurPoserCanalPerso) qui retourne le coût de poser son propre canal
	 * Si le constructeur à un solde suffisant alors le choix lui est proposé
	 * 
	 * Cas 1: Appelle la fonction soutenirJoueur qui affiche la liste des propositions et retourne un joueur choisi
	 * 		  Ajoute le pot de vin au solde du constructeur de canal
	 * 
	 * Cas 2: Pose le canal et déduit le coût au solde
	 * 
	 * @return SantiagoInterface joueurChoisi: Le joueur choisi
	 */
	@Override
	public SantiagoInterface choisirPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException {
		int coutCanalPerso;
		boolean choixOk = false;
		int choixProposition = 0;
		SantiagoInterface joueurChoisi = null;
		
		//Fonction pour vérifier si le constructeur a la possibilité de poser son propre canal
		coutCanalPerso = constructeurPoserCanalPerso(listePropositions);	
		
		while(!choixOk) {
			if(coutCanalPerso + 1 < joueur.getSolde()) {
				System.out.println("Faites un choix:");
				System.out.println("[1] Choisir la proposition d'un joueur");
				System.out.println("[2] Choisir de constuire son propre canal (Coût: Proposition la plus élevée + 1 Escudo");	
				
				try {
					choixProposition = scInt.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Erreur dans la valeur. \n");
				}
				
			} else {
				System.out.println("Vous n'avez pas un solde suffisant pour poser votre propre canal.");
				System.out.println("Vous devez donc poser le canal d'un joueur :");
				choixProposition = 1;
			}
			
			
			switch(choixProposition) {
				case 1:
					choixOk = true;
					joueurChoisi = this.soutenirJoueur(listePropositions);
					
					for(SantiagoInterface si : listePropositions.keySet()) {
						if(si.equals(joueurChoisi)) {
							try {
								joueur.setSolde(joueur.getSolde() + listePropositions.get(si));
							} catch (JoueurException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							
						}
					}
					
					break;
				case 2:
					choixOk = true;
					System.out.println("Vous avez décidé de poser votre propre canal. Son coût est de " + (coutCanalPerso + 1) + " Escudos");
					joueurChoisi = this;
					System.out.println("Choisir la position du canal:");
					
					try {
						joueur.setSolde(joueur.getSolde() - coutCanalPerso - 1);
					} catch (JoueurException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				default:
					System.out.println("Le choix indiqué n'existe pas ...");
			}	
		}
		return joueurChoisi;
	}
	
	/**
	 * Cette fonction est appelé à la fin de la phase 4
	 * Si le constructeur de canal a choisi de construire son propre canal, ne fait rien
	 * Sinon parcourt la liste des soutiens pour leur déduire le pot de vin de soutien
	 * Puis déduit le pot de vin du joueur qui a créé la proposition de base
	 */
	@Override
	public void deduirePotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens, HashMap<SantiagoInterface, Integer> listePotsDeVin, SantiagoInterface constructeur) throws RemoteException{
		if(!this.equals(constructeur)) {
			// On déduit le pot de vin des joueurs ayant soutenu la proposition
			for(SantiagoInterface si : listeSoutiens.keySet()) {
				if(listeSoutiens.get(si).equals(this)) {
					for(SantiagoInterface siSoutien : listePotsDeVin.keySet()) {
						if(si.equals(siSoutien)) {
							try {
								si.getJoueur().setSolde(si.getJoueur().getSolde() - listePotsDeVin.get(siSoutien));
							} catch (RemoteException | JoueurException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}		
				}
			}
			
			// On déduit le pot de vin du joueur ayant fait la proposition
			for(SantiagoInterface si : listePotsDeVin.keySet()) {
				if(si.equals(this)) {
					try {
						si.getJoueur().setSolde(si.getJoueur().getSolde() - listePotsDeVin.get(si));
					} catch (RemoteException | JoueurException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}
		

	}

	/**
	 * Calcule le cout pour le constructeur de poser son canal perso (Basé sur la proposition max + 1)
	 * @param listePropositions
	 * @return int: propositionMax
	 */
	public int constructeurPoserCanalPerso(HashMap<SantiagoInterface, Integer> listePropositions) {
		int propositionMax = 0;
		
		for(SantiagoInterface si : listePropositions.keySet()) {
			if(listePropositions.get(si) > propositionMax) {
				propositionMax = listePropositions.get(si);							
			}
		}
		
		return propositionMax;
	}
	
	
	/**
	 * Fonction qui affiche une erreur
	 * @param : String message
	 * @return : void
	 */
	public void afficheErreur(String message) throws RemoteException{
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		System.out.println(message);
	}
	
	/**
	 * Fonction qui enregitre le pseudo d'un utilisateur
	 * @param : String pseudo
	 * @return : void
	 */
	public void enregistrePseudo(String pseudo) throws RemoteException{
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		listePseudos.add(pseudo);
	}
	
	/**
	 * Fonction qui dit si un pseudo est dispo ou non
	 * @params : String pseudo
	 * @return : boolean
	 */
	public boolean pseudoEstDisponible(String pseudo) throws RemoteException{
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		return !(listePseudos.contains(pseudo));
	}
	
	
	
	
	
	/**
	 * Fonction qui test si une partie est prête à être lancée.
	 * Si une partie est prête à être lancée, on lance la partie, 
	 * Sinon on ne fait rien
	 * @param : Partie partie que l'on veut tester
	 * @throws PartieException 
	 * @throws RemoteException 
	 */
	public void testPartieEstPrete(Partie partie) throws PartieException, RemoteException{
		
		/*Pour qu'une partie soit prête, il faut : 
		*
		*	- Que le nombre de rejoueur requis soit atteint
		*	- Qu'elle n'ait pas déjà commencé (bien évidemment)
		*/
		if(partie.getNombreJoueursRequis() == partie.getNombreJoueurDansLaPartie()){
			
			if(! partie.getPartieACommence()){
				
				//On lance la partie
				partie.lancePartie();
			}
			
		}
	}
	
	
	
	
	
	/**
	 * Fonction qui vérifie si une partie est présente dans une liste de partie
	 * @parameters : arraylist<Partie> , String nomPartie
	 */
	public boolean listeContientPartie(ArrayList<Partie> liste, String nomPartie){
		
		for(Partie p : liste){
			
			if(p.getNomPartie().equals(nomPartie)){
				
				return true;
			}
		}
		
		return false;
	}
	
	

	
	/**
	 * Fonction appelée par le serveur uniquement pour mettre à jour la liste des parties
	 * Appelé lorsque le client appelle la fonction creerPartie().
	 */
	@Override
	public void ajouterPartieListe(Partie p) throws RemoteException {
		// TODO Auto-generated method stub
		listeParties.add(p);
	}

	
	
	
	/**
	 * Fonction appelée par le client pour voir la liste des parties en cours sur le serveur
	 * @return: ArrayList listeParties
	 */
	@Override
	public ArrayList<Partie> voirParties() throws RemoteException {
		// TODO Auto-generated method stub
		return listeParties;
	}
	
	
}
