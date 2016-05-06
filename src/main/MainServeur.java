package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.Joueur;
import Classes.Partie;
import Exception.JoueurException;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;


public class MainServeur {
	
	public static void main (String args[]) throws RemoteException, MalformedURLException, PartieException, JoueurException
	{
		LocateRegistry.createRegistry(44000);
		System.setProperty ("java.rmi.server.hostname",	"192.168.43.93");
		
		Santiago server	= new Santiago("serveur");
		
		Naming.rebind("rmi://192.168.43.93:44000/ABC",server);
		
		System.out.println("[System] Santiago remote object is ready");
		

		while(true)
		{
			try {
				Thread.sleep(10000);
				server.testPartieEstPrete();
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
