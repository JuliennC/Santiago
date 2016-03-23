package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import Classes.*;
import Exception.JoueurException;
import Exception.PartieException;
import network.SantiagoInterface;
import network.Serveur;


public class MainClient{
	
	
	static private Joueur joueur;
	
	
	public static void main (String args[]) throws RemoteException, MalformedURLException, NotBoundException, JoueurException, PartieException{
	
		joueur = new Joueur();
		
		String ipAdress = "127.0.0.1";
		
		System.setSecurityManager(new SecurityManager());
		
		//On demande les informations au joueur
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez votre pseudo de joueur"); 
		String pseudo = sc.nextLine().trim();
		
		//On met les informations du joueur
		joueur.setPseudo(pseudo);
				
		
		//On demande si l'utilisateur veut rejoindre ou créer une partie
		System.out.println("Voulez-vous créer [1] ou rejoindre une partie [2] ?"); 
		String res = sc.nextLine().trim();
		
		
		SantiagoInterface client = joueur;
		
		//On lui définie l'addresse du serveur
		Serveur serveur =	(Serveur)Naming.lookup("rmi://"+ipAdress+":42000/ABC");
		serveur.test();
		//serveur.addClient(client);
		
		
		//On fait suivant le resultat
		if (res.equals("1")) {
			
			System.out.println("Entrez le nombre de joueur :"); 
			int nb = sc.nextInt();
			
			Partie nouvellePartie = new Partie(nb);
			
			//serveur.addPartie(nouvellePartie);
			
			
		} else {
			
			
			
		}
		
		
		
		
		
		
		while(true) {
			
			
			
			
		}
		
		
	}
	
	
	

	

}
