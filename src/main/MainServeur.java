package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import network.Santiago;
import network.SantiagoInterface;


public class MainServeur {
	public static void main (String args[]) throws RemoteException, MalformedURLException
	{
		String ipAdress = "127.0.0.1";
		
		LocateRegistry.createRegistry(42000);
		System.setProperty ("java.rmi.server.hostname",	ipAdress);

		Santiago serveur = new Santiago();
		Naming.rebind("rmi://"+ipAdress+":42000/ABC",serveur);
		
		while(true)
		{
			if(serveur.getClient()!=null)
			{
				SantiagoInterface client = serveur.getClient();
			}
		
		}
		
	}
}
