import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends UnicastRemoteObject implements ClientInterface {
	// Attribute
	private JFrame frame;
	private Serverinterface server;
	private Fensterpanel spielpanel;
	private String host = "rmi://" + "127.0.0.1" + ":" + Registry.REGISTRY_PORT;

	public GUI() throws RemoteException {
		frame = new JFrame("GUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1280, 720);
		spielpanel = new Fensterpanel();
		frame.add(spielpanel);
		frame.setVisible(true);

	}
	//Konstruktor für den Multiplayer
	public GUI(String host) throws MalformedURLException, RemoteException, NotBoundException {
		frame = new JFrame("GUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 700);
		server = (Serverinterface) Naming.lookup("Server");
		server.registPlayer(this);
		frame.setVisible(true);

	}
	
	// Methoden
	@Override
	public void update(Fensterpanel neuesPanel, String currenPlayer) throws RemoteException {
		//Löscht das alte panel
		if (this.spielpanel != null)
			frame.getContentPane().remove(this.spielpanel);
		//fügt das neue Panel hinzu und aktualisiert die GUI
		this.spielpanel = neuesPanel;
		frame.setTitle("Schiffeversenken            	" + currenPlayer);
		frame.getContentPane().add(this.spielpanel);
		frame.revalidate();
		//Fügt den neuen Buttons einen Listener hinzu
		JButton[][] gS = this.spielpanel.getgS();
		JButton[][] mS = this.spielpanel.getmS();
		for (int j = 0; j < spielpanel.getgS().length; j++) {
			for (int i = 0; i <spielpanel.getgS()[j].length; i++) {
				if (j !=0 && i !=0) {
					buttonListener(gS[j][i], j,i);
					ButtonAction(mS[j][i], j,i);
				}
			}
		}
	}
	public void buttonListener(JButton button, int zeile, int spalte) {	// Listener fürs Feld gS (gegnerische Schiffe)
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schissen(zeile,spalte);
			}
		});
	}
	public void ButtonAction(JButton button, int zeile, int spalte) {	// Listener fürs Feld mS (meine Schiffe)
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setzeSchiff(zeile,spalte);
			}
		});
	}

	private void schissen(int zeile,int spalte) {	//zum schissen auf gegnerische Schiffe
		try {
			server.schuss(zeile,spalte, this);
		} catch (RemoteException e) {
			System.out.println("Connection lost");
		}
	}
	private void setzeSchiff(int zeile,int spalte) {	// zum setzten von Schiffen
		try {
			server.setzen(zeile, spalte, this);
		} catch (RemoteException e) {
			System.out.println("Connection lost");
		}
	}
}