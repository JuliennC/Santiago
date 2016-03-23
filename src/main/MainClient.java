package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import network.Santiago;
import network.SantiagoInterface;


public class MainClient {
	public static void main (String args[]) throws RemoteException, MalformedURLException, NotBoundException{
		String ipAdress = "127.0.0.1";
		
		System.setSecurityManager(new SecurityManager());
		
		SantiagoInterface client = new Santiago();
		SantiagoInterface serveur =	(SantiagoInterface)Naming.lookup("rmi://"+ipAdress+":42000/ABC");
		
		serveur.setClient(client);
		
		while(true)
		{		

		}
		
		
	}

}
