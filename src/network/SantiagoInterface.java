package network;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SantiagoInterface extends Remote{

	public void setClient(SantiagoInterface b)	throws RemoteException;
	public SantiagoInterface getClient() throws RemoteException;
}
