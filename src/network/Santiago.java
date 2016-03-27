package network;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import Classes.Joueur;
import Classes.Partie;
import Exception.PartieException;


public class Santiago extends UnicastRemoteObject implements SantiagoInterface {
	Scanner scString = new Scanner(System.in);
	Scanner scInt = new Scanner(System.in);
	
	private ArrayList<Partie> listeParties = new ArrayList<>();
	
	
	protected String name;
	private String pseudo;
	private SantiagoInterface client = null;
	
	public Santiago(String n) throws RemoteException {
		super();
		this.name = n;
	}
	
	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("La partie a été créée");
		
		return p;
		
	}

	/**
	 * Fonction appelée par le client pour rejoindre une partie
	 * Le client renseigne le nom de la partie à rejoindre et son objet Joueur
	 * @return: booléen de confirmation
	 */
	@Override
	public boolean rejoindrePartie(String nom, Joueur j) throws RemoteException {
		// TODO Auto-generated method stub
		for(Partie p:listeParties) {
			if(p.getNomPartie().equals(nom)) {
				try {
					p.addJoueur(j);
				} catch (PartieException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
