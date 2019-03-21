import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server  {
	
	//Konstruktor
	public Server() throws RemoteException, MalformedURLException, AlreadyBoundException {
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		Spiel spiel = new Spiel();
		Naming.bind("Server", spiel);
		System.out.println("Sever started");
	}
	//Methoden
	public void newstart() throws RemoteException, MalformedURLException, NotBoundException, AlreadyBoundException {
		//Löscht das alte Logik Obejkt und fügt ein neues hinzu
		Naming.unbind("Server");
		Spiel spiel = new Spiel();
		Naming.bind("Server", spiel);
	}
	
	



	
	
}
