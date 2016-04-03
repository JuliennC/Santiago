package network;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Classes.Joueur;
import Classes.Partie;
import Exception.PartieException;


public interface SantiagoInterface extends Remote{
	
	public String getName() throws RemoteException;
	
	public SantiagoInterface getClient() throws RemoteException;
	public void setClient(SantiagoInterface s)	throws RemoteException;
	
	public void send(String msg) throws RemoteException;
	
	public Partie creerPartie() throws RemoteException;
	public void ajouterPartieListe(Partie p) throws RemoteException;
	
	public ArrayList<Partie> voirParties() throws RemoteException;
	
	public String rejoindrePartie(String nom, Joueur j) throws RemoteException, PartieException;

	public void enregistrePseudo(String pseudo) throws RemoteException;

	public boolean pseudoEstDisponible(String pseudo) throws RemoteException;
}