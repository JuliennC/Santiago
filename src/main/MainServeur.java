package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.Joueur;
import Classes.Partie;
import network.Santiago;
import network.SantiagoInterface;


public class MainServeur {
	public static void main (String args[]) throws RemoteException, MalformedURLException
	{
		LocateRegistry.createRegistry(43000);
		System.setProperty ("java.rmi.server.hostname",	"127.0.0.1");
		
		Santiago server	= new Santiago("serveur");
		
		Naming.rebind("rmi://127.0.0.1:43000/ABC",server);
		
		System.out.println("[System] Santiago remote object is ready");
		
		while(true)
		{
			//Ne rentre pas dans la condition
			//if(server.getClient()!=null)
			//{
			//	SantiagoInterface client = server.getClient();
			//	String pseudo = server.reception();
				
			//	System.out.println("Pseudo du joueur: " +pseudo);
			//}
		
		}
	}
}
