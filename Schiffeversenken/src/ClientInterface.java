
import java.rmi.Remote;
import java.rmi.RemoteException;
//Interface f�r die Client seite des Verteilten Systems
public interface ClientInterface extends Remote {
	

	public void update(Fensterpanel panel, String currentPlayer) throws RemoteException;

}