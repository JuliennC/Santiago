package network;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Classes.Partie;


public interface SantiagoInterface extends Remote{

	public void addClient(SantiagoInterface s)	throws RemoteException;
	public ArrayList<SantiagoInterface> getClients() throws RemoteException;
}
