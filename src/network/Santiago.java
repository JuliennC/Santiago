package network;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Santiago extends UnicastRemoteObject implements SantiagoInterface {

	private SantiagoInterface client = null;

	public Santiago() throws RemoteException {

	}

	@Override
	public void setClient(SantiagoInterface b) throws RemoteException {
		client = b;

	}

	@Override
	public SantiagoInterface getClient() throws RemoteException {
		// TODO Auto-generated method stub
		return client;
	}
}
