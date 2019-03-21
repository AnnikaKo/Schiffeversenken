
import java.rmi.Remote;
import java.rmi.RemoteException;
//Interface für die Server Seite des Verteilten systems
public interface Serverinterface extends Remote {
	
	public void registPlayer(ClientInterface spieler)throws RemoteException;
	
	public void setzen(int zeile,int spalte, ClientInterface sender) throws RemoteException;
	public void schuss(int zeile,int spalte,ClientInterface sender)throws RemoteException;

}