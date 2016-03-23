package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import Classes.*;
import network.SantiagoInterface;
import network.Serveur;


public class MainServeur {
	
	public static void main (String args[]) throws RemoteException, MalformedURLException
	{
		
	
		
		String ipAdress = "127.0.0.1";
		
		LocateRegistry.createRegistry(42000);
		System.setSecurityManager(new SecurityManager()); 
		System.setProperty ("java.rmi.server.hostname",	ipAdress);

		Serveur serveur = new Serveur();
		Naming.rebind("rmi://127.0.0.1:42000/ABC",serveur); 
		
		
		
	}
	
	
	
}
